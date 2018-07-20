package protocolsupport.protocol.serializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackRemapper;
import protocolsupport.protocol.utils.NBTTagCompoundSerializer;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NetworkItemStack;

public class ItemStackSerializer {

	public static NetworkItemStack readItemStack(ByteBuf from, ProtocolVersion version, String locale, boolean isFromClient) {
		int type = from.readShort();
		if (type < 0) {
			return NetworkItemStack.NULL;
		}
		NetworkItemStack itemstack = new NetworkItemStack();
		itemstack.setTypeId(type);
		itemstack.setAmount(from.readByte());
		if (version.isBefore(ProtocolVersion.MINECRAFT_1_13)) {
			itemstack.setLegacyData(from.readUnsignedShort());
		}
		itemstack.setNBT(readTag(from, version));
		if (isFromClient) {
			itemstack = ItemStackRemapper.remapFromClient(version, locale, itemstack);
		}
		return itemstack;
	}

	public static void writeItemStack(ByteBuf to, ProtocolVersion version, String locale, NetworkItemStack itemstack, boolean isToClient) {
		if (itemstack.isNull()) {
			to.writeShort(-1);
			return;
		}
		NetworkItemStack witemstack = itemstack;
		if (isToClient) {
			witemstack = ItemStackRemapper.remapToClient(version, locale, witemstack.cloneItemStack());
			//TODO: fire new itemstack write event here
		}
		to.writeShort(witemstack.getTypeId());
		to.writeByte(witemstack.getAmount());
		if (version.isBefore(ProtocolVersion.MINECRAFT_1_13)) {
			to.writeShort(witemstack.getLegacyData());
		}
		writeTag(to, version, witemstack.getNBT());
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

}
