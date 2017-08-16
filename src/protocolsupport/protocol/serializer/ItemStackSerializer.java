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
		int type = from.readShort();
		if (type >= 0) {
			ItemStackWrapper itemstack = ServerPlatform.get().getWrapperFactory().createItemStack(type);
			itemstack.setAmount(from.readByte());
			itemstack.setData(from.readUnsignedShort());
			itemstack.setTag(readTag(from, version));
			if (isFromClient) {
				itemstack = ItemStackRemapper.remapFromClient(version, locale, itemstack.cloneItemStack());
			}
			return itemstack;
		}
		return ServerPlatform.get().getWrapperFactory().createNullItemStack();
	}

	public static void writeItemStack(ByteBuf to, ProtocolVersion version, String locale, ItemStackWrapper itemstack, boolean isToClient) {
		if (itemstack.isNull()) {
			to.writeShort(-1);
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
		to.writeShort(witemstack.getTypeId());
		to.writeByte(witemstack.getAmount());
		to.writeShort(witemstack.getData());
		writeTag(to, version, witemstack.getTag());
	}

	public static NBTTagCompoundWrapper readTag(ByteBuf from, ProtocolVersion version) {
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
			} else {
				throw new IllegalArgumentException(MessageFormat.format("Dont know how to read nbt of version {0}", version));
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
						NBTTagCompoundSerializer.writeTag(new DataOutputStream(outputstream), tag);
					}
					//now replace fake length with real length
					to.setShort(writerIndex, to.writerIndex() - writerIndex - Short.BYTES);
				}
			} else if (isUsingDirectNBT(version)) {
				NBTTagCompoundSerializer.writeTag(new ByteBufOutputStream(to), tag);
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
