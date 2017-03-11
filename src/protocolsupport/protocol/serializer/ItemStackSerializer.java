package protocolsupport.protocol.serializer;

import java.io.DataInputStream;
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
import protocolsupport.protocol.utils.PENetworkNBTDataOutputStream;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class ItemStackSerializer {

	public static ItemStackWrapper readItemStack(ByteBuf from, ProtocolVersion version) {
		int type = from.readShort();
		if (type >= 0) {
			ItemStackWrapper itemstack = ServerPlatform.get().getWrapperFactory().createItemStack(type);
			itemstack.setAmount(from.readByte());
			itemstack.setData(from.readShort());
			itemstack.setTag(readTag(from, version));
			return ItemStackRemapper.remapServerbound(version, itemstack.cloneItemStack());
		}
		return ServerPlatform.get().getWrapperFactory().createNullItemStack();
	}

	public static void writeItemStack(ByteBuf to, ProtocolVersion version, ItemStackWrapper itemstack) {
		if (itemstack.isNull()) {
			to.writeShort(-1);
			return;
		}
		ItemStackWrapper remapped = ItemStackRemapper.remapClientbound(version, itemstack.cloneItemStack());
		if (ItemStackWriteEvent.getHandlerList().getRegisteredListeners().length > 0) {
			ItemStackWriteEvent event = new InternalItemStackWriteEvent(version, itemstack, remapped);
			Bukkit.getPluginManager().callEvent(event);
		}
		to.writeShort(ItemStackRemapper.ITEM_ID_REMAPPING_REGISTRY.getTable(version).getRemap(remapped.getTypeId()));
		to.writeByte(remapped.getAmount());
		to.writeShort(remapped.getData());
		writeTag(to, version, remapped.getTag());
	}

	public static NBTTagCompoundWrapper readTag(ByteBuf from, ProtocolVersion version) {
		try {
			if (isUsingShortLengthNBT(version)) {
				final short length = from.readShort();
				if (length < 0) {
					return ServerPlatform.get().getWrapperFactory().createNullNBTCompound();
				}
				try (InputStream inputstream = new GZIPInputStream(new ByteBufInputStream(from.readSlice(length)))) {
					return ServerPlatform.get().getWrapperFactory().createNBTCompoundFromStream(inputstream);
				}
			} else if (isUsingDirectOrZeroIfNoneNBT(version)) {
				from.markReaderIndex();
				if (from.readByte() == 0) {
					return ServerPlatform.get().getWrapperFactory().createNullNBTCompound();
				}
				from.resetReaderIndex();
				try (DataInputStream datainputstream = new DataInputStream(new ByteBufInputStream(from))) {
					return ServerPlatform.get().getWrapperFactory().createNBTCompoundFromStream(datainputstream);
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
						tag.writeToStream(outputstream);
					}
					//now replace fake length with real length
					to.setShort(writerIndex, to.writerIndex() - writerIndex - Short.BYTES);
				}
			} else if (isUsingDirectOrZeroIfNoneNBT(version)) {
				if (tag.isNull()) {
					to.writeByte(0);
				} else {
					try (OutputStream outputstream = new ByteBufOutputStream(to)) {
						tag.writeToStream(outputstream);
					}
				}
			} else if (isUsingPENBT(version)) {
				tag.writeToStream(new PENetworkNBTDataOutputStream(to));
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

	private static final boolean isUsingDirectOrZeroIfNoneNBT(ProtocolVersion version) {
		return (version.getProtocolType() == ProtocolType.PC) && version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_8);
	}

	private static final boolean isUsingPENBT(ProtocolVersion version) {
		return (version.getProtocolType() == ProtocolType.PE) && (version == ProtocolVersion.MINECRAFT_PE);
	}

	public static class InternalItemStackWriteEvent extends ItemStackWriteEvent {

		private final org.bukkit.inventory.ItemStack wrapped;
		public InternalItemStackWriteEvent(ProtocolVersion version, ItemStackWrapper original, ItemStackWrapper itemstack) {
			super(version, original.asBukkitMirror());
			this.wrapped = itemstack.asBukkitMirror();
		}

		@Override
		public org.bukkit.inventory.ItemStack getResult() {
			return wrapped;
		}

	}

}
