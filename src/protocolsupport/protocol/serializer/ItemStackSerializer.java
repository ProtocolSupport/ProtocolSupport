package protocolsupport.protocol.serializer;

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
				int amountdata = VarNumberSerializer.readSVarInt(from);
				itemstack.setAmount(amountdata & 0x7F);
				itemstack.setData((amountdata >> 8) & 0xFFFF);
				itemstack.setTag(readTag(from, version));
				//TODO: Read the rest properly..
				ArraySerializer.readVarIntStringArray(from, version); //TODO: CanPlaceOn PE
				ArraySerializer.readVarIntStringArray(from, version); //TODO: CanDestroy PE
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
		if (itemstack.isNull()) {
			if (version == ProtocolVersion.MINECRAFT_PE) {
				VarNumberSerializer.writeVarInt(to, -1);
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
			writeTag(to, version, witemstack.getTag()); //TODO: Remap PE NBT
			to.writeByte(0);
			to.writeByte(0);
			//VarNumberSerializer.writeVarInt(to, 0); //TODO: CanPlaceOn PE
			//VarNumberSerializer.writeVarInt(to, 0); //TODO: CanDestroy PE
		} else {
			to.writeShort(witemstack.getTypeId());
			to.writeByte(witemstack.getAmount());
			to.writeShort(witemstack.getData());
			writeTag(to, version, witemstack.getTag());
		}
	}

	public static NBTTagCompoundWrapper readTag(ByteBuf from, ProtocolVersion version) {
		try {
			if (isUsingShortLengthNBT(version)) {
				final short length = from.readShort();
				if (length < 0) {
					return NBTTagCompoundWrapper.NULL;
				}
				try (InputStream inputstream = new GZIPInputStream(new ByteBufInputStream(from.readSlice(length)))) {
					return NBTTagCompoundSerializer.readTag(inputstream);
				}
			} else if (isUsingDirectNBT(version)) {
				try (InputStream inputstream = new ByteBufInputStream(from)) {
					return NBTTagCompoundSerializer.readTag(inputstream);
				}
			} else {
				throw new IllegalArgumentException(MessageFormat.format("Don't know how to read nbt of version {0}", version));
			}
		} catch (IOException e) {
			throw new DecoderException(e);
		}
	}

	public static void writeTag(ByteBuf to, ProtocolVersion version, NBTTagCompoundWrapper tag) {
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
						NBTTagCompoundSerializer.writeTag(outputstream, tag);
					}
					//now replace fake length with real length
					to.setShort(writerIndex, to.writerIndex() - writerIndex - Short.BYTES);
				}
			} else if (isUsingDirectNBT(version)) {
				try (OutputStream outputstream = new ByteBufOutputStream(to)) {
					NBTTagCompoundSerializer.writeTag(outputstream, tag);
				}
			} else if (isUsingPENBT(version)) {
				if (tag.isNull()) {
					to.writeShort(0);
				} else {
					int writerIndex = to.writerIndex();
					//fake length
					to.writeShortLE(0);
					//actual nbt
					//tag.writeToStream(new PENetworkNBTDataOutputStream(to)); //TODO Remap and write PE NBT.
					//now replace fake length with real length
					to.setShortLE(writerIndex, to.writerIndex() - writerIndex - Short.BYTES);
				}
			} else {
				throw new IllegalArgumentException(MessageFormat.format("Don't know how to write nbt of version {0}", version));
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
