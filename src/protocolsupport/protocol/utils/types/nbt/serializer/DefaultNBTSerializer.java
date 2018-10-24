package protocolsupport.protocol.utils.types.nbt.serializer;

import java.io.DataInput;
import java.io.DataOutput;
import java.util.List;
import java.util.Map.Entry;

import io.netty.handler.codec.DecoderException;
import protocolsupport.protocol.utils.types.nbt.NBT;
import protocolsupport.protocol.utils.types.nbt.NBTByte;
import protocolsupport.protocol.utils.types.nbt.NBTByteArray;
import protocolsupport.protocol.utils.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.types.nbt.NBTDouble;
import protocolsupport.protocol.utils.types.nbt.NBTEnd;
import protocolsupport.protocol.utils.types.nbt.NBTFloat;
import protocolsupport.protocol.utils.types.nbt.NBTInt;
import protocolsupport.protocol.utils.types.nbt.NBTIntArray;
import protocolsupport.protocol.utils.types.nbt.NBTList;
import protocolsupport.protocol.utils.types.nbt.NBTLong;
import protocolsupport.protocol.utils.types.nbt.NBTLongArray;
import protocolsupport.protocol.utils.types.nbt.NBTShort;
import protocolsupport.protocol.utils.types.nbt.NBTString;
import protocolsupport.protocol.utils.types.nbt.NBTType;

public class DefaultNBTSerializer extends NBTSerializer<DataInput, DataOutput> {

	public static final DefaultNBTSerializer INSTANCE = new DefaultNBTSerializer();

	@SuppressWarnings("unchecked")
	public DefaultNBTSerializer() {
		super(DataInput::readByte, DataOutput::writeByte, DataInput::readUTF, DataOutput::writeUTF);
		registerType(NBTType.END, 0, stream -> NBTEnd.INSTANCE, (stream, tag) -> {});
		registerType(NBTType.BYTE, 1, stream -> new NBTByte(stream.readByte()), (stream, tag) -> stream.writeByte(tag.getAsByte()));
		registerType(NBTType.SHORT, 2, stream -> new NBTShort(stream.readShort()), (stream, tag) -> stream.writeShort(tag.getAsShort()));
		registerType(NBTType.INT, 3, stream -> new NBTInt(stream.readInt()), (stream, tag) -> stream.writeInt(tag.getAsInt()));
		registerType(NBTType.LONG, 4, stream -> new NBTLong(stream.readLong()), (stream, tag) -> stream.writeLong(tag.getAsLong()));
		registerType(NBTType.FLOAT, 5, stream -> new NBTFloat(stream.readFloat()), (stream, tag) -> stream.writeFloat(tag.getAsFloat()));
		registerType(NBTType.DOUBLE, 6, stream -> new NBTDouble(stream.readDouble()), (stream, tag) -> stream.writeDouble(tag.getAsDouble()));
		registerType(NBTType.STRING, 8, stream -> new NBTString(stream.readUTF()), (stream, tag) -> stream.writeUTF(tag.getValue()));
		registerType(
			NBTType.BYTE_ARRAY, 7,
			stream -> {
				byte[] array = new byte[stream.readInt()];
				stream.readFully(array);
				return new NBTByteArray(array);
			},
			(stream, tag) -> {
				byte[] array = tag.getValue();
				stream.writeInt(array.length);
				stream.write(array);
			}
		);
		registerType(
			NBTType.INT_ARRAY, 11,
			stream -> {
				int[] array = new int[stream.readInt()];
				for (int i = 0; i < array.length; i++) {
					array[i] = stream.readInt();
				}
				return new NBTIntArray(array);
			},
			(stream, tag) -> {
				int[] array = tag.getValue();
				stream.writeInt(array.length);
				for (int i : array) {
					stream.writeInt(i);
				}
			}
		);
		registerType(
			NBTType.LONG_ARRAY, 12,
			stream -> {
				long[] array = new long[stream.readInt()];
				for (int i = 0; i < array.length; i++) {
					array[i] = stream.readLong();
				}
				return new NBTLongArray(array);
			},
			(stream, tag) -> {
				long[] array = tag.getValue();
				stream.writeInt(array.length);
				for (long i : array) {
					stream.writeLong(i);
				}
			}
		);
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
		registerType(
			NBTType.LIST, 9,
			stream -> {
				NBTType<? extends NBT> valueType = readTagType(stream);
				int size = stream.readInt();
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
				stream.writeInt(tag.size());
				for (NBT value : ((List<NBT>) tag.getTags())) {
					writeTag(stream, value);
				}
			}
		);
	}

}
