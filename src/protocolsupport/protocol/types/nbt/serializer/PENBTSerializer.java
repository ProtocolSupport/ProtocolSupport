package protocolsupport.protocol.types.nbt.serializer;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map.Entry;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.nbt.serializer.NBTSerializer;
import protocolsupport.protocol.types.nbt.NBT;
import protocolsupport.protocol.types.nbt.NBTByte;
import protocolsupport.protocol.types.nbt.NBTByteArray;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTDouble;
import protocolsupport.protocol.types.nbt.NBTEnd;
import protocolsupport.protocol.types.nbt.NBTFloat;
import protocolsupport.protocol.types.nbt.NBTInt;
import protocolsupport.protocol.types.nbt.NBTIntArray;
import protocolsupport.protocol.types.nbt.NBTList;
import protocolsupport.protocol.types.nbt.NBTLong;
import protocolsupport.protocol.types.nbt.NBTShort;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.types.nbt.NBTType;

public class PENBTSerializer extends NBTSerializer<ByteBuf, ByteBuf> {

	public static final PENBTSerializer VI_INSTANCE = new PENBTSerializer(true);
	public static final PENBTSerializer LE_INSTANCE = new PENBTSerializer(false);

	@SuppressWarnings("unchecked")
	public PENBTSerializer(boolean varint) {
		super(ByteBuf::readByte, ByteBuf::writeByte, getPEStringReader(varint), getPEStringWriter(varint));
		registerType(NBTType.END, 0, stream -> NBTEnd.INSTANCE, (stream, tag) -> {});
		registerType(NBTType.BYTE, 1, stream -> new NBTByte(stream.readByte()), (stream, tag) -> stream.writeByte(tag.getAsByte()));
		registerType(NBTType.SHORT, 2, stream -> new NBTShort(stream.readShortLE()), (stream, tag) -> stream.writeShortLE(tag.getAsShort()));
		registerType(NBTType.FLOAT, 5, stream -> new NBTFloat(stream.readFloatLE()), (stream, tag) -> stream.writeFloatLE(tag.getAsFloat()));
		registerType(NBTType.DOUBLE, 6, stream -> new NBTDouble(stream.readDoubleLE()), (stream, tag) -> stream.writeDoubleLE(tag.getAsDouble()));
		registerType(
			NBTType.COMPOUND, 10,
			stream -> {
				NBTCompound compound = new NBTCompound();
				NBTType<?> valueType = null;
				while ((valueType = readTagType(stream)) != NBTType.END) {
					compound.setTag(readTagName(stream), readTag(stream, valueType));
				}
				return compound;
			},
			(stream, tag) -> {
				for (Entry<String, NBT> entry : tag.getTags().entrySet()) {
					NBT value = entry.getValue();
					writeTagType(stream, value.getType());
					writeTagName(stream, entry.getKey());
					writeTag(stream, value);
				}
				writeTagType(stream, NBTType.END);
			}
		);
		// Pe has two similar but different formats.
		if (varint) {
			registerType(NBTType.INT, 3, stream -> new NBTInt(VarNumberSerializer.readSVarInt(stream)), (stream, tag) -> VarNumberSerializer.writeSVarInt(stream, tag.getAsInt()));
			registerType(NBTType.LONG, 4, stream -> new NBTLong(VarNumberSerializer.readSVarLong(stream)), (stream, tag) -> VarNumberSerializer.writeSVarLong(stream, tag.getAsLong()));
			registerType(
				NBTType.STRING, 8,
				stream -> {
					return new NBTString(new String(MiscSerializer.readBytes(stream, VarNumberSerializer.readVarInt(stream)), StandardCharsets.UTF_8));
				},
				(stream, tag) -> {
					byte[] data = tag.getValue().getBytes(StandardCharsets.UTF_8);
					VarNumberSerializer.writeVarInt(stream, data.length);
					stream.writeBytes(data);
				});
			registerType(
				NBTType.BYTE_ARRAY, 7,
				stream -> {
					byte[] array = new byte[VarNumberSerializer.readSVarInt(stream)];
					for (int i = 0; i < array.length; i++) {
						array[i] = stream.readByte();
					}
					return new NBTByteArray(array);
				},
				(stream, tag) -> {
					byte[] array = tag.getValue();
					VarNumberSerializer.writeSVarInt(stream, array.length);
					stream.writeBytes(array);
				}
			);
			registerType(
				NBTType.INT_ARRAY, 11,
				stream -> {
					int[] array = new int[VarNumberSerializer.readSVarInt(stream)];
					for (int i = 0; i < array.length; i++) {
						array[i] = VarNumberSerializer.readSVarInt(stream);
					}
					return new NBTIntArray(array);
				},
				(stream, tag) -> {
					int[] array = tag.getValue();
					VarNumberSerializer.writeSVarInt(stream, array.length);
					for (int i : array) {
						VarNumberSerializer.writeSVarInt(stream, i);
					}
				}
			);
			registerType(
				NBTType.LIST, 9,
				stream -> {
					NBTType<? extends NBT> valueType = readTagType(stream);
					int size = VarNumberSerializer.readSVarInt(stream);
					if ((valueType == NBTType.END) && (size > 0)) {
						throw new DecoderException("Missing nbt list values tag type");
					}
					NBTList<NBT> list = new NBTList<>((NBTType<NBT>) valueType);
					for (int i = 0; i < size; i++) {
						list.addTag(readTag(stream, valueType));
					}
					return list;
				},
				(stream, tag) -> {
					writeTagType(stream, tag.getTagsType());
					VarNumberSerializer.writeSVarInt(stream, tag.size());
					for (NBT value : ((List<NBT>) tag.getTags())) {
						writeTag(stream, value);
					}
				}
			);
		} else {
			registerType(NBTType.INT, 3, stream -> new NBTInt(stream.readIntLE()), (stream, tag) -> stream.writeIntLE(tag.getAsInt()));
			registerType(NBTType.LONG, 4, stream -> new NBTLong(stream.readLongLE()), (stream, tag) -> stream.writeLongLE(tag.getAsLong()));
			registerType(
				NBTType.STRING, 8,
				stream -> {
					return new NBTString(new String(MiscSerializer.readBytes(stream, stream.readShortLE()), StandardCharsets.UTF_8));
				},
				(stream, tag) -> {
					byte[] data = tag.getValue().getBytes(StandardCharsets.UTF_8);
					stream.writeShortLE(data.length);
					stream.writeBytes(data);
				});
			registerType(
				NBTType.BYTE_ARRAY, 7,
				stream -> {
					byte[] array = new byte[stream.readIntLE()];
					for (int i = 0; i < array.length; i++) {
						array[i] = stream.readByte();
					}
					return new NBTByteArray(array);
				},
				(stream, tag) -> {
					byte[] array = tag.getValue();
					stream.writeIntLE(array.length);
					stream.writeBytes(array);
				}
			);
			registerType(
				NBTType.INT_ARRAY, 11,
				stream -> {
					int[] array = new int[stream.readIntLE()];
					for (int i = 0; i < array.length; i++) {
						array[i] = stream.readIntLE();
					}
					return new NBTIntArray(array);
				},
				(stream, tag) -> {
					int[] array = tag.getValue();
					stream.writeIntLE(array.length);
					for (int i : array) {
						stream.writeIntLE(i);
					}
				}
			);
			registerType(
				NBTType.LIST, 9,
				stream -> {
					NBTType<? extends NBT> valueType = readTagType(stream);
					int size = stream.readIntLE();
					if ((valueType == NBTType.END) && (size > 0)) {
						throw new DecoderException("Missing nbt list values tag type");
					}
					NBTList<NBT> list = new NBTList<>((NBTType<NBT>) valueType);
					for (int i = 0; i < size; i++) {
						list.addTag(readTag(stream, valueType));
					}
					return list;
				},
				(stream, tag) -> {
					writeTagType(stream, tag.getTagsType());
					stream.writeIntLE(tag.size());
					for (NBT value : ((List<NBT>) tag.getTags())) {
						writeTag(stream, value);
					}
				}
			);
		}
	}

	private static NameReader<ByteBuf> getPEStringReader(boolean varint) {
		return (stream) -> {
			int length = 0;
			if (varint) {
				length = VarNumberSerializer.readVarInt(stream);
			} else {
				length = stream.readShortLE();
			}
			return new String(MiscSerializer.readBytes(stream, length), StandardCharsets.UTF_8);
		};
	}

	private static NameWriter<ByteBuf> getPEStringWriter(boolean varint) {
		return (stream, string) -> {
			byte[] data = string.getBytes(StandardCharsets.UTF_8);
			if (varint) {
				VarNumberSerializer.writeVarInt(stream, data.length);
			} else {
				stream.writeShortLE(data.length);
			}
			stream.writeBytes(data);
		};
	}

}
