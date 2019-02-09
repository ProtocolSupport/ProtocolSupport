package protocolsupport.protocol.serializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
import protocolsupport.protocol.utils.ItemStackWriteEventHelper;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.types.NetworkItemStack;
import protocolsupport.protocol.utils.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.types.nbt.NBTEnd;
import protocolsupport.protocol.utils.types.nbt.serializer.DefaultNBTSerializer;

public class ItemStackSerializer {

	/**
	 * Reads server itemstack (latest protocol version format) <br>
	 * @param from buffer to read from
	 * @return itemstack
	 */
	public static NetworkItemStack readItemStack(ByteBuf from) {
		if (!from.readBoolean()) {
			return NetworkItemStack.NULL;
		}
		NetworkItemStack itemstack = new NetworkItemStack();
		itemstack.setTypeId(VarNumberSerializer.readVarInt(from));
		itemstack.setAmount(from.readByte());
		itemstack.setNBT(readTag(from));
		return itemstack;
	}

	/**
	 * Reads client itemstack (provided protocol version format) <br>
	 * @param from buffer to read from
	 * @param version protocol version
	 * @param locale client locale
	 * @return itemstack
	 */
	public static NetworkItemStack readItemStack(ByteBuf from, ProtocolVersion version, String locale) {
		int type;
		if (version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_13_2)) {
			boolean exist = from.readBoolean();
			if (!exist) {
				return NetworkItemStack.NULL;
			}
			type = VarNumberSerializer.readVarInt(from);
		} else {
			type = from.readShort();
			if (type < 0) {
				return NetworkItemStack.NULL;
			}
		}
		NetworkItemStack itemstack = new NetworkItemStack();
		itemstack.setTypeId(type);
		itemstack.setAmount(from.readByte());
		if (version.isBefore(ProtocolVersion.MINECRAFT_1_13)) {
			itemstack.setLegacyData(from.readUnsignedShort());
		}
		itemstack.setNBT(readTag(from, version));
		itemstack = ItemStackRemapper.remapFromClient(version, locale, itemstack);
		return itemstack;
	}

	/**
	 * Writes server itemstack (latest protocol version format)
	 * @param to buffer to write to
	 * @param itemstack itemstack
	 */
	public static void writeItemStack(ByteBuf to, NetworkItemStack itemstack) {
		if (itemstack.isNull()) {
			to.writeBoolean(false);
		} else {
			to.writeBoolean(true);
			VarNumberSerializer.writeVarInt(to, itemstack.getTypeId());
			to.writeByte(itemstack.getAmount());
			writeTag(to, ProtocolVersionsHelper.LATEST_PC, itemstack.getNBT());
		}
	}

	/**
	 * Writes client itemstack (provided protocol version format)
	 * @param to buffer to write to
	 * @param version protocol version
	 * @param locale client locale
	 * @param itemstack itemstack
	 */
	public static void writeItemStack(ByteBuf to, ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		if (itemstack.isNull()) {
			if (version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_13_2)) {
				to.writeBoolean(false);
			} else {
				to.writeShort(-1);
			}
			return;
		}

		ItemStackWriteEventHelper.callEvent(version, locale, itemstack);

		itemstack = ItemStackRemapper.remapToClient(version, locale, itemstack);

		if (version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_13_2)) {
			to.writeBoolean(true);
			VarNumberSerializer.writeVarInt(to, itemstack.getTypeId());
		} else {
			to.writeShort(itemstack.getTypeId());
		}
		to.writeByte(itemstack.getAmount());
		if (version.isBefore(ProtocolVersion.MINECRAFT_1_13)) {
			to.writeShort(itemstack.getLegacyData());
		}
		writeTag(to, version, itemstack.getNBT());
	}

	/**
	 * Reads server nbt (latest protocol version format)
	 * @param from buffer to read form
	 * @return nbt
	 */
	public static NBTCompound readTag(ByteBuf from) {
		try {
			return DefaultNBTSerializer.INSTANCE.deserializeTag(new ByteBufInputStream(from));
		} catch (IOException e) {
			throw new DecoderException(e);
		}
	}

	/**
	 * Reads client nbt (provided protocol version)
	 * @param from buffer to read form
	 * @param version protocol version
	 * @return nbt
	 */
	public static NBTCompound readTag(ByteBuf from, ProtocolVersion version) {
		try {
			if (isUsingShortLengthNBT(version)) {
				final short length = from.readShort();
				if (length < 0) {
					return null;
				}
				try (DataInputStream stream = new DataInputStream(new GZIPInputStream(new ByteBufInputStream(from.readSlice(length))))) {
					return DefaultNBTSerializer.INSTANCE.deserializeTag(stream);
				}
			} else if (isUsingDirectNBT(version)) {
				return DefaultNBTSerializer.INSTANCE.deserializeTag(new ByteBufInputStream(from));
			} else {
				throw new IllegalArgumentException(MessageFormat.format("Dont know how to read nbt of version {0}", version));
			}
		} catch (IOException e) {
			throw new DecoderException(e);
		}
	}

	public static void writeTag(ByteBuf to, ProtocolVersion version, NBTCompound tag) {
		if (isUsingShortLengthNBT(version)) {
			if (tag == null) {
				to.writeShort(-1);
			} else {
				MiscSerializer.writeLengthPrefixedBytes(
					to,
					(lTo, length) -> lTo.writeShort(length),
					lTo -> {
						try (DataOutputStream outputstream = new DataOutputStream(new GZIPOutputStream(new ByteBufOutputStream(lTo)))) {
							DefaultNBTSerializer.INSTANCE.serializeTag(outputstream, tag);
						} catch (Exception e) {
							throw new EncoderException(e);
						}
					}
				);
			}
		} else if (isUsingDirectNBT(version)) {
			try (ByteBufOutputStream outputstream = new ByteBufOutputStream(to)) {
				if (tag != null) {
					DefaultNBTSerializer.INSTANCE.serializeTag(outputstream, tag);
				} else {
					DefaultNBTSerializer.INSTANCE.serializeTag(outputstream, NBTEnd.INSTANCE);
				}
			} catch (IOException e) {
				throw new EncoderException(e);
			}
		} else {
			throw new IllegalArgumentException(MessageFormat.format("Dont know how to write nbt of version {0}", version));
		}
	}

	private static final boolean isUsingShortLengthNBT(ProtocolVersion version) {
		return (version.getProtocolType() == ProtocolType.PC) && version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_7_10);
	}

	private static final boolean isUsingDirectNBT(ProtocolVersion version) {
		return (version.getProtocolType() == ProtocolType.PC) && version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_8);
	}

}
