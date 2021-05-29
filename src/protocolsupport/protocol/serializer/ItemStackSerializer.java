package protocolsupport.protocol.serializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackRemappingHelper;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTEnd;
import protocolsupport.protocol.types.nbt.serializer.DefaultNBTSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.SimpleTypeDeserializer;
import protocolsupport.protocol.utils.SimpleTypeSerializer;
import protocolsupportbuildprocessor.Preload;

@Preload
public class ItemStackSerializer {

	private ItemStackSerializer() {
	}

	public static final SimpleTypeDeserializer<NetworkItemStack> ITEMSTACK_DESERIALIZER = new SimpleTypeDeserializer<>(
		new AbstractMap.SimpleEntry<>(ItemStackSerializer::readItemStack, ProtocolVersion.getAllAfterI(ProtocolVersion.MINECRAFT_1_13_2)),
		new AbstractMap.SimpleEntry<>(from -> {
			int typeId = from.readShort();
			if (typeId < 0) {
				return NetworkItemStack.NULL;
			} else {
				NetworkItemStack itemstack = new NetworkItemStack();
				itemstack.setTypeId(typeId);
				itemstack.setAmount(from.readByte());
				itemstack.setNBT(readDirectTag(from));
				return itemstack;
			}
		}, ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_13, ProtocolVersion.MINECRAFT_1_13_1)),
		new AbstractMap.SimpleEntry<>(from -> {
			int typeId = from.readShort();
			if (typeId < 0) {
				return NetworkItemStack.NULL;
			} else {
				NetworkItemStack itemstack = new NetworkItemStack();
				itemstack.setTypeId(typeId);
				itemstack.setAmount(from.readByte());
				itemstack.setLegacyData(from.readShort());
				itemstack.setNBT(readDirectTag(from));
				return itemstack;
			}
		}, ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_12_2, ProtocolVersion.MINECRAFT_1_8)),
		new AbstractMap.SimpleEntry<>(from -> {
			int typeId = from.readShort();
			if (typeId < 0) {
				return NetworkItemStack.NULL;
			} else {
				NetworkItemStack itemstack = new NetworkItemStack();
				itemstack.setTypeId(typeId);
				itemstack.setAmount(from.readByte());
				itemstack.setLegacyData(from.readShort());
				itemstack.setNBT(readShortTag(from));
				return itemstack;
			}
		}, ProtocolVersionsHelper.DOWN_1_7_10)
	);

	public static final SimpleTypeSerializer<NetworkItemStack> ITEMSTACK_SERIALIZER = new SimpleTypeSerializer<>(
		new AbstractMap.SimpleEntry<>(ItemStackSerializer::writeItemStack, ProtocolVersion.getAllAfterI(ProtocolVersion.MINECRAFT_1_13_2)),
		new AbstractMap.SimpleEntry<>((to, itemstack) -> {
			if (itemstack.isNull()) {
				to.writeShort(-1);
			} else {
				to.writeShort(itemstack.getTypeId());
				to.writeByte(itemstack.getAmount());
				writeDirectTag(to, itemstack.getNBT());
			}
		}, ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_13, ProtocolVersion.MINECRAFT_1_13_1)),
		new AbstractMap.SimpleEntry<>((to, itemstack) -> {
			if (itemstack.isNull()) {
				to.writeShort(-1);
			} else {
				to.writeShort(itemstack.getTypeId());
				to.writeByte(itemstack.getAmount());
				to.writeShort(itemstack.getLegacyData());
				writeDirectTag(to, itemstack.getNBT());
			}
		}, ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_12_2, ProtocolVersion.MINECRAFT_1_8)),
		new AbstractMap.SimpleEntry<>((to, itemstack) -> {
			if (itemstack.isNull()) {
				to.writeShort(-1);
			} else {
				to.writeShort(itemstack.getTypeId());
				to.writeByte(itemstack.getAmount());
				to.writeShort(itemstack.getLegacyData());
				writeShortTag(to, itemstack.getNBT());
			}
		}, ProtocolVersionsHelper.DOWN_1_7_10)
	);

	/**
	 * Reads itemstack using latest protocol version format
	 * @param from buffer to read from
	 * @return itemstack itemstack
	 */
	public static NetworkItemStack readItemStack(ByteBuf from) {
		if (!from.readBoolean()) {
			return NetworkItemStack.NULL;
		}
		NetworkItemStack itemstack = new NetworkItemStack();
		itemstack.setTypeId(VarNumberSerializer.readVarInt(from));
		itemstack.setAmount(from.readByte());
		itemstack.setNBT(readDirectTag(from));
		return itemstack;
	}

	/**
	 * Reads itemstack using provided protocol version format<br>
	 * @param from buffer to read from
	 * @param version protocol version
	 * @return itemstack itemstack
	 */
	public static NetworkItemStack readItemStack(ByteBuf from, ProtocolVersion version) {
		NetworkItemStack itemstack = ITEMSTACK_DESERIALIZER.get(version).apply(from);
		if (!itemstack.isNull()) {
			itemstack = ItemStackRemappingHelper.fromLegacyItemFormat(version, itemstack);
		}
		return itemstack;
	}


	/**
	 * Writes itemstack using latest protocol version format
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
	 * Writes itemstack using provided protocol version format
	 * @param to buffer to write to
	 * @param version protocol version
	 * @param itemstack itemstack
	 */
	public static void writeItemStack(ByteBuf to, ProtocolVersion version, NetworkItemStack itemstack) {
		ITEMSTACK_SERIALIZER.get(version).accept(to, itemstack);
	}

	/**
	 * Transforms and writes itemstack using provided protocol version format
	 * @param to buffer to write to
	 * @param version protocol version
	 * @param locale client locale
	 * @param itemstack itemstack
	 */
	public static void writeItemStack(ByteBuf to, ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		writeItemStack(to, version, ItemStackRemappingHelper.toLegacyItemDataFormat(version, locale, itemstack));
	}



	public static final SimpleTypeDeserializer<NBTCompound> TAG_DESERIALIZER = new SimpleTypeDeserializer<>(
		new AbstractMap.SimpleEntry<>(ItemStackSerializer::readDirectTag, ProtocolVersionsHelper.UP_1_8),
		new AbstractMap.SimpleEntry<>(ItemStackSerializer::readShortTag, ProtocolVersionsHelper.DOWN_1_7_10)
	);

	public static final SimpleTypeSerializer<NBTCompound> TAG_SERIALIZER = new SimpleTypeSerializer<>(
		new AbstractMap.SimpleEntry<>(ItemStackSerializer::writeDirectTag, ProtocolVersionsHelper.UP_1_8),
		new AbstractMap.SimpleEntry<>(ItemStackSerializer::writeShortTag, ProtocolVersionsHelper.DOWN_1_7_10)
	);

	public static NBTCompound readTag(ByteBuf from, ProtocolVersion version) {
		return TAG_DESERIALIZER.get(version).apply(from);
	}

	public static NBTCompound readDirectTag(ByteBuf from) {
		try {
			return (NBTCompound) DefaultNBTSerializer.INSTANCE.deserializeTag(new ByteBufInputStream(from));
		} catch (IOException e) {
			throw new DecoderException(e);
		}
	}

	public static NBTCompound readShortTag(ByteBuf from) {
		try {
			final short length = from.readShort();
			if (length < 0) {
				return null;
			}
			try (DataInputStream stream = new DataInputStream(new GZIPInputStream(new ByteBufInputStream(from.readSlice(length))))) {
				return (NBTCompound) DefaultNBTSerializer.INSTANCE.deserializeTag(stream);
			}
		} catch (IOException e) {
			throw new DecoderException(e);
		}
	}

	/**
	 * Writes tag using provided protocol version format
	 * @param to buffer to write to
	 * @param version protocol version
	 * @param tag nbt compound
	 */
	public static void writeTag(ByteBuf to, ProtocolVersion version, NBTCompound tag) {
		TAG_SERIALIZER.get(version).accept(to, tag);
	}

	/**
	 * Writes tag using latest protocol version format (Directly serializes nbt to buffer).
	 * @param to buffer to write to
	 * @param tag nbt compound
	 */
	public static void writeDirectTag(ByteBuf to, NBTCompound tag) {
		try (ByteBufOutputStream outputstream = new ByteBufOutputStream(to)) {
			if (tag != null) {
				DefaultNBTSerializer.INSTANCE.serializeTag(outputstream, tag);
			} else {
				DefaultNBTSerializer.INSTANCE.serializeTag(outputstream, NBTEnd.INSTANCE);
			}
		} catch (IOException e) {
			throw new EncoderException(e);
		}
	}

	/**
	 * Writes tag using legacy protocol version format (Prefixes serialized nbt data with short length).
	 * @param to buffer to write to
	 * @param tag nbt compound
	 */
	public static void  writeShortTag(ByteBuf to, NBTCompound tag) {
		if (tag == null) {
			to.writeShort(-1);
		} else {
			MiscSerializer.writeShortLengthPrefixedType(to, tag, (lTo, lTag) -> {
				try (DataOutputStream outputstream = new DataOutputStream(new GZIPOutputStream(new ByteBufOutputStream(lTo)))) {
					DefaultNBTSerializer.INSTANCE.serializeTag(outputstream, lTag);
				} catch (Exception e) {
					throw new EncoderException(e);
				}
			});
		}
	}

}
