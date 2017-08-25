package protocolsupport.protocol.serializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.bukkit.Bukkit;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.events.ItemStackWriteEvent;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackRemapper;
import protocolsupport.protocol.utils.NBTTagCompoundSerializer;
import protocolsupport.utils.IntTuple;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class ItemStackSerializer {

	public static ItemStackWrapper readItemStack(ByteBuf from, ProtocolVersion version, String locale, boolean isFromClient) {
		int type = 0;
		if (version == ProtocolVersion.MINECRAFT_PE) {
			type = VarNumberSerializer.readSVarInt(from);
		} else {
			type = from.readShort();
		}
		if (type >= 0) {
			ItemStackWrapper itemstack = ServerPlatform.get().getWrapperFactory().createItemStack(type);
			if (version == ProtocolVersion.MINECRAFT_PE) {
				if(type == 0) { //Non or empty item stacks can also be 0 in PE.
					return ServerPlatform.get().getWrapperFactory().createNullItemStack();
				}
				int amountdata = VarNumberSerializer.readSVarInt(from);
				itemstack.setAmount(amountdata & 0x7F);
				itemstack.setData((amountdata >> 8) & 0xFFFF);
				itemstack.setTag(readTag(from, false, version));
				//TODO: Read the rest properly..
				from.readByte(); //TODO: CanPlaceOn PE
				from.readByte(); //TODO: CanDestroy PE
			} else {
				itemstack.setAmount(from.readByte());
				itemstack.setData(from.readUnsignedShort());
				itemstack.setTag(readTag(from, version));
			}
			if (isFromClient) {
				itemstack = ItemStackRemapper.remapFromClient(version, locale, itemstack.cloneItemStack());
			}
			return itemstack;
		}
		return ServerPlatform.get().getWrapperFactory().createNullItemStack();
	}

	public static void writeItemStack(ByteBuf to, ProtocolVersion version, String locale, ItemStackWrapper itemstack, boolean isToClient) {
		if (itemstack == null || itemstack.isNull()) {
			if (version == ProtocolVersion.MINECRAFT_PE) {
				VarNumberSerializer.writeVarInt(to, 0);
			} else {
				to.writeShort(-1);
			}
			return;
		}
		ItemStackWrapper witemstack = itemstack;
		if (isToClient) {
			witemstack = witemstack.cloneItemStack();
			IntTuple iddata = ItemStackRemapper.ID_DATA_REMAPPING_REGISTRY.getTable(version).getRemap(witemstack.getTypeId(), witemstack.getData());
			if (iddata != null) {
				witemstack.setTypeId(iddata.getI1());
				if (iddata.getI2() != -1) {
					witemstack.setData(iddata.getI2());
				}
			}
			if (ItemStackWriteEvent.getHandlerList().getRegisteredListeners().length > 0) {
				ItemStackWriteEvent event = new InternalItemStackWriteEvent(version, locale, itemstack, witemstack);
				Bukkit.getPluginManager().callEvent(event);
			}
			witemstack = ItemStackRemapper.remapToClient(version, locale, itemstack.getTypeId(), witemstack);
		}
		if (version == ProtocolVersion.MINECRAFT_PE) {
			VarNumberSerializer.writeSVarInt(to, witemstack.getTypeId()); //TODO: Remap PE itemstacks...
			VarNumberSerializer.writeSVarInt(to, ((witemstack.getData() & 0xFFFF) << 8) | witemstack.getAmount());
			writeTag(to, false, version, witemstack.getTag()); //TODO: write and Remap PE NBT
			to.writeByte(0); //TODO: CanPlaceOn PE
			to.writeByte(0); //TODO: CanDestroy PE
		} else {
			to.writeShort(witemstack.getTypeId());
			to.writeByte(witemstack.getAmount());
			to.writeShort(witemstack.getData());
			writeTag(to, version, witemstack.getTag());
		}
	}

	public static NBTTagCompoundWrapper readTag(ByteBuf from, ProtocolVersion version) {
		return readTag(from, false, version);
	}

	public static NBTTagCompoundWrapper readTag(ByteBuf from, boolean varint, ProtocolVersion version) {
		try {
			if (isUsingShortLengthNBT(version)) {
				final short length = from.readShort();
				if (length < 0) {
					return NBTTagCompoundWrapper.NULL;
				}
				try (InputStream inputstream = new GZIPInputStream(new ByteBufInputStream(from.readSlice(length)))) {
					return NBTTagCompoundSerializer.readTag(new DataInputStream(inputstream));
				}
			} else if (isUsingDirectNBT(version)) {
				return NBTTagCompoundSerializer.readTag(new ByteBufInputStream(from));
			} else if (isUsingPENBT(version)) {
				if (!varint) { // VarInts NBTs doesn't have length
					final short length = from.readShortLE();
					if (length <= 0) {
						return NBTTagCompoundWrapper.NULL;
					}
				}
				return NBTTagCompoundSerializer.readPeTag(from, varint);
			} else {
				throw new IllegalArgumentException(MessageFormat.format("Dont know how to read nbt of version {0}", version));
			}
		} catch (IOException e) {
			throw new DecoderException(e);
		}
	}

	public static void writeTag(ByteBuf to, ProtocolVersion version, NBTTagCompoundWrapper tag) {
		writeTag(to, false, version, tag);
	}

	public static void writeTag(ByteBuf to, boolean varint, ProtocolVersion version, NBTTagCompoundWrapper tag) {
		try {
			if (isUsingShortLengthNBT(version)) {
				if (tag.isNull()) {
					to.writeShort(-1);
				} else {
					int writerIndex = to.writerIndex();
					//fake length
					to.writeShort(0);
					//actual nbt
					try (OutputStream outputstream = new GZIPOutputStream(new ByteBufOutputStream(to))) {
						NBTTagCompoundSerializer.writeTag(new DataOutputStream(outputstream), tag);
					}
					//now replace fake length with real length
					to.setShort(writerIndex, to.writerIndex() - writerIndex - Short.BYTES);
				}
			} else if (isUsingDirectNBT(version)) {
				NBTTagCompoundSerializer.writeTag(new ByteBufOutputStream(to), tag);
			} else if (isUsingPENBT(version)) {
				if (tag.isNull()) {
					to.writeShortLE(0);
				} else {
					int writerIndex = to.writerIndex();
					//fake length
					if (!varint) { // VarInt NBTs doesn't have length
						to.writeShortLE(0);
					}
					//actual nbt
					NBTTagCompoundSerializer.writePeTag(to, varint, tag); //TODO Remap PE NBT?
					//now replace fake length with real length
					if (!varint) {
						to.setShortLE(writerIndex, to.writerIndex() - writerIndex - Short.BYTES);
					}
				}
			} else {
				throw new IllegalArgumentException(MessageFormat.format("Dont know how to write nbt of version {0}", version));
			}
		} catch (Throwable ioexception) {
			throw new EncoderException(ioexception);
		}
	}

	private static final boolean isUsingShortLengthNBT(ProtocolVersion version) {
		return (version.getProtocolType() == ProtocolType.PC) && version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_7_10);
	}

	private static final boolean isUsingDirectNBT(ProtocolVersion version) {
		return (version.getProtocolType() == ProtocolType.PC) && version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_8);
	}

	private static final boolean isUsingPENBT(ProtocolVersion version) {
		return (version.getProtocolType() == ProtocolType.PE) && (version == ProtocolVersion.MINECRAFT_PE);
	}

	public static class InternalItemStackWriteEvent extends ItemStackWriteEvent {

		private final org.bukkit.inventory.ItemStack wrapped;
		public InternalItemStackWriteEvent(ProtocolVersion version, String locale, ItemStackWrapper original, ItemStackWrapper itemstack) {
			super(version, locale, original.asBukkitMirror());
			this.wrapped = itemstack.asBukkitMirror();
		}

		@Override
		public org.bukkit.inventory.ItemStack getResult() {
			return wrapped;
		}

	}

}
