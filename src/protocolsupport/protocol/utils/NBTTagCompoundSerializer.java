package protocolsupport.protocol.utils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;

public class NBTTagCompoundSerializer {

	public static void writeTag(DataOutput os, NBTTagCompoundWrapper tag) throws IOException {
		if (tag.isNull()) {
			os.writeByte(NBTTagType.END.getId());
			return;
		}
		writeTagHeader(os, "", NBTTagType.COMPOUND);
		writeCompoundPayload(os, tag);
	}

	private static void writeTagHeader(DataOutput os, String name, NBTTagType tag) throws IOException {
		os.writeByte(tag.getId());
		os.writeUTF(name);
	}

	private static void writeCompoundPayload(DataOutput os, NBTTagCompoundWrapper tag) throws IOException {
		for (String key : tag.getKeys()) {
			NBTTagType type = tag.getTagType(key);
			writeTagHeader(os, key, type);
			switch (type) {
				case BYTE: {
					os.writeByte(tag.getIntNumber(key));
					break;
				}
				case SHORT: {
					os.writeShort(tag.getIntNumber(key));
					break;
				}
				case INT: {
					os.writeInt(tag.getIntNumber(key));
					break;
				}
				case LONG: {
					os.writeLong(tag.getLongNumber(key));
					break;
				}
				case FLOAT: {
					os.writeFloat(tag.getFloatNumber(key));
					break;
				}
				case DOUBLE: {
					os.writeDouble(tag.getDoubleNumber(key));
					break;
				}
				case BYTE_ARRAY: {
					byte[] array = tag.getByteArray(key);
					os.writeInt(array.length);
					os.write(array);
					break;
				}
				case INT_ARRAY: {
					int[] array = tag.getIntArray(key);
					os.writeInt(array.length);
					for (int v : array) {
						os.writeInt(v);
					}
					break;
				}
				case LONG_ARRAY: {
					long[] array = tag.getLongArray(key);
					os.writeInt(array.length);
					for (long v : array) {
						os.writeLong(v);
					}
					break;
				}
				case STRING: {
					os.writeUTF(tag.getString(key));
					break;
				}
				case COMPOUND: {
					writeCompoundPayload(os, tag.getCompound(key));
					break;
				}
				case LIST: {
					writeListPayload(os, tag.getList(key));
					break;
				}
				default: {
					throw new IOException(MessageFormat.format("Unknown or unsupported tag type {0}", type));
				}
			}
		}
		os.writeByte(NBTTagType.END.getId());
	}

	private static void writeListPayload(DataOutput os, NBTTagListWrapper tag) throws IOException {
		NBTTagType type = tag.getType();
		os.writeByte(type.getId());
		os.writeInt(tag.size());
		for (int i = 0; i < tag.size(); i++) {
			switch (type) {
				case END: {
					break;
				}
				case BYTE: {
					os.writeByte(tag.getIntNumber(i));
					break;
				}
				case SHORT: {
					os.writeShort(tag.getIntNumber(i));
					break;
				}
				case INT: {
					os.writeInt(tag.getIntNumber(i));
					break;
				}
				case LONG: {
					os.writeLong(tag.getLongNumber(i));
					break;
				}
				case FLOAT: {
					os.writeFloat(tag.getFloatNumber(i));
					break;
				}
				case DOUBLE: {
					os.writeDouble(tag.getDoubleNumber(i));
					break;
				}
				case BYTE_ARRAY: {
					byte[] array = tag.getByteArray(i);
					os.writeInt(array.length);
					os.write(array);
					break;
				}
				case INT_ARRAY: {
					int[] array = tag.getIntArray(i);
					os.writeInt(array.length);
					for (int v : array) {
						os.writeInt(v);
					}
					break;
				}
				case LONG_ARRAY: {
					long[] array = tag.getLongArray(i);
					os.writeInt(array.length);
					for (long v : array) {
						os.writeLong(v);
					}
					break;
				}
				case STRING: {
					os.writeUTF(tag.getString(i));
					break;
				}
				case COMPOUND: {
					writeCompoundPayload(os, tag.getCompound(i));
					break;
				}
				case LIST: {
					writeListPayload(os, tag.getList(i));
					break;
				}
				default: {
					throw new IOException(MessageFormat.format("Unknown or unsupported tag type {0}", type));
				}
			}
		}
	}


	public static NBTTagCompoundWrapper readTag(DataInput is) throws IOException {
		NBTTagType type = NBTTagType.fromId(is.readByte());
		if (type == NBTTagType.END) {
			return NBTTagCompoundWrapper.NULL;
		}
		if (type != NBTTagType.COMPOUND) {
			throw new IOException(MessageFormat.format("Root tag must be compound, got: {0}", type));
		}
		is.readUTF();
		NBTTagCompoundWrapper tag = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
		readCompoundPayload(is, tag);
		return tag;
	}

	private static void readCompoundPayload(DataInput is, NBTTagCompoundWrapper tag) throws IOException {
		NBTTagType type = null;
		while ((type = NBTTagType.fromId(is.readByte())) != NBTTagType.END) {
			String name = is.readUTF();
			switch (type) {
				case BYTE: {
					tag.setByte(name, is.readByte());
					break;
				}
				case SHORT: {
					tag.setShort(name, is.readShort());
					break;
				}
				case INT: {
					tag.setInt(name, is.readInt());
					break;
				}
				case LONG: {
					tag.setLong(name, is.readLong());
					break;
				}
				case FLOAT: {
					tag.setFloat(name, is.readFloat());
					break;
				}
				case DOUBLE: {
					tag.setDouble(name, is.readDouble());
					break;
				}
				case BYTE_ARRAY: {
					byte[] array = new byte[is.readInt()];
					is.readFully(array);
					tag.setByteArray(name, array);
					break;
				}
				case INT_ARRAY: {
					int[] array = new int[is.readInt()];
					for (int j = 0; j < array.length; j++) {
						array[j] = is.readInt();
					}
					tag.setIntArray(name, array);
					break;
				}
				case LONG_ARRAY: {
					long[] array = new long[is.readInt()];
					for (int j = 0; j < array.length; j++) {
						array[j] = is.readLong();
					}
					tag.setLongArray(name, array);
					break;
				}
				case STRING: {
					tag.setString(name, is.readUTF());
					break;
				}
				case COMPOUND: {
					NBTTagCompoundWrapper compound = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
					readCompoundPayload(is, compound);
					tag.setCompound(name, compound);
					break;
				}
				case LIST: {
					NBTTagListWrapper list = ServerPlatform.get().getWrapperFactory().createEmptyNBTList();
					readListPayload(is, list);
					tag.setList(name, list);
					break;
				}
				default: {
					throw new IOException(MessageFormat.format("Unknown or unsupported tag type {0}", type));
				}
			}
		}
	}

	private static void readListPayload(DataInput is, NBTTagListWrapper tag) throws IOException {
		NBTTagType type = NBTTagType.fromId(is.readByte());
		int size = is.readInt();
		if ((type == NBTTagType.END) && (size > 0)) {
			throw new IOException("Missing type");
		}
		for (int i = 0; i < size; i++) {
			switch (type) {
				case BYTE: {
					tag.addByte(is.readByte());
					break;
				}
				case SHORT: {
					tag.addShort(is.readShort());
					break;
				}
				case INT: {
					tag.addInt(is.readInt());
					break;
				}
				case LONG: {
					tag.addLong(is.readLong());
					break;
				}
				case FLOAT: {
					tag.addFloat(is.readFloat());
					break;
				}
				case DOUBLE: {
					tag.addDouble(is.readDouble());
					break;
				}
				case BYTE_ARRAY: {
					byte[] array = new byte[is.readInt()];
					is.readFully(array);
					tag.addByteArray(array);
					break;
				}
				case INT_ARRAY: {
					int[] array = new int[is.readInt()];
					for (int j = 0; j < array.length; j++) {
						array[j] = is.readInt();
					}
					tag.addIntArray(array);
					break;
				}
				case LONG_ARRAY: {
					long[] array = new long[is.readInt()];
					for (int j = 0; j < array.length; j++) {
						array[j] = is.readLong();
					}
					tag.addLongArray(array);
					break;
				}
				case STRING: {
					tag.addString(is.readUTF());
					break;
				}
				case COMPOUND: {
					NBTTagCompoundWrapper compound = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
					readCompoundPayload(is, compound);
					tag.addCompound(compound);
					break;
				}
				case LIST: {
					NBTTagListWrapper list = ServerPlatform.get().getWrapperFactory().createEmptyNBTList();
					readListPayload(is, list);
					tag.addList(list);
					break;
				}
				default: {
					throw new IOException(MessageFormat.format("Unknown or unsupported tag type {0}", type));
				}
			}
		}
	}

	public static void writePeTag(ByteBuf os, boolean varint, NBTTagCompoundWrapper tag) throws IOException {
		System.out.println("Writing NBT... Varint: " + varint + " tag: " + tag);
		if (tag.isNull()) {
			os.writeByte(NBTTagType.END.getId());
			return;
		}
		writePeTagHeader(os, varint, "", NBTTagType.COMPOUND);
		writePeCompoundPayload(os, varint, tag);
	}

	private static void writePeTagHeader(ByteBuf os, boolean varint, String name, NBTTagType tag) throws IOException {
		os.writeByte(tag.getId());
		writePeNBTString(os, varint, name);
	}

	private static void writePeCompoundPayload(ByteBuf os, boolean varint, NBTTagCompoundWrapper tag) throws IOException {
		for (String key : tag.getKeys()) {
			NBTTagType type = tag.getTagType(key);
			writePeTagHeader(os, varint, key, type);
			switch (type) {
				case BYTE: {
					os.writeByte(tag.getIntNumber(key));
					break;
				}
				case SHORT: {
					os.writeShortLE(tag.getIntNumber(key));
					break;
				}
				case INT: {
					writePeNBTSInt(os, varint, tag.getIntNumber(key));
					break;
				}
				case LONG: {
					os.writeLongLE(tag.getLongNumber(key));
					break;
				}
				case FLOAT: {
					os.writeFloatLE(tag.getFloatNumber(key));
					break;
				}
				case DOUBLE: {
					os.writeDoubleLE(tag.getDoubleNumber(key));
					break;
				}
				case BYTE_ARRAY: {
					byte[] array = tag.getByteArray(key);
					writePeNBTInt(os, varint, array.length);
					os.writeBytes(array);
					break;
				}
				case INT_ARRAY: {
					int[] array = tag.getIntArray(key);
					writePeNBTInt(os, varint, array.length);
					for (int v : array) {
						writePeNBTSInt(os, varint, v);
					}
					break;
				}
				//Not in PE (Yet) :S
				/*case LONG_ARRAY: {
					long[] array = tag.getLongArray(key);
					VarNumberSerializer.writeVarInt(os, array.length);
					for (long v : array) {
						VarNumberSerializer.writeSVarInt(os, (int) v); //Cast that sh*t.
					}
					break;
				}*/
				case STRING: {
					writePeNBTString(os, varint, tag.getString(key));
					break;
				}
				case COMPOUND: {
					writePeCompoundPayload(os, varint, tag.getCompound(key));
					break;
				}
				case LIST: {
					writePeListPayload(os, varint, tag.getList(key));
					break;
				}
				default: {
					throw new IOException(MessageFormat.format("Unknown or unsupported tag type {0}", type));
				}
			}
		}
		os.writeByte(NBTTagType.END.getId());
	}

	private static void writePeListPayload(ByteBuf os, boolean varint, NBTTagListWrapper tag) throws IOException {
		NBTTagType type = tag.getType();
		os.writeByte(type.getId());
		writePeNBTSInt(os, varint, tag.size());
		for (int i = 0; i < tag.size(); i++) {
			switch (type) {
				case END: {
					break;
				}
				case BYTE: {
					os.writeByte(tag.getIntNumber(i));
					break;
				}
				case SHORT: {
					os.writeShortLE(tag.getIntNumber(i));
					break;
				}
				case INT: {
					writePeNBTSInt(os, varint, tag.getIntNumber(i));
					break;
				}
				case LONG: {
					os.writeLongLE(tag.getLongNumber(i));
					break;
				}
				case FLOAT: {
					os.writeFloatLE(tag.getFloatNumber(i));
					break;
				}
				case DOUBLE: {
					os.writeDoubleLE(tag.getDoubleNumber(i));
					break;
				}
				case BYTE_ARRAY: {
					byte[] array = tag.getByteArray(i);
					writePeNBTInt(os, varint, array.length);
					os.writeBytes(array);
					break;
				}
				case INT_ARRAY: {
					int[] array = tag.getIntArray(i);
					writePeNBTInt(os, varint, array.length);
					for (int v : array) {
						writePeNBTSInt(os, varint, v);
					}
					break;
				}
				//Not in PE (Yet) :S
				/*case LONG_ARRAY: {
					long[] array = tag.getLongArray(i);
					os.writeInt(array.length);
					for (long v : array) {
						os.writeLong(v);
					}
					break;
				}*/
				case STRING: {
					writePeNBTString(os, varint, tag.getString(i));
					break;
				}
				case COMPOUND: {
					writePeCompoundPayload(os, varint, tag.getCompound(i));
					break;
				}
				case LIST: {
					writePeListPayload(os, varint, tag.getList(i));
					break;
				}
				default: {
					throw new IOException(MessageFormat.format("Unknown or unsupported tag type {0}", type));
				}
			}
		}
	}

	public static NBTTagCompoundWrapper readPeTag(ByteBuf is, boolean varint) throws IOException {
		NBTTagType type = NBTTagType.fromId(is.readByte());
		if (type == NBTTagType.END) {
			return NBTTagCompoundWrapper.NULL;
		}
		if (type != NBTTagType.COMPOUND) {
			throw new IOException(MessageFormat.format("Root tag must be compound, got: {0}", type));
		}
		readPeNBTString(is, varint);
		NBTTagCompoundWrapper tag = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
		readPeCompoundPayload(is, varint, tag);
		return tag;
	}

	private static void readPeCompoundPayload(ByteBuf is, boolean varint, NBTTagCompoundWrapper tag) throws IOException {
		NBTTagType type = null;
		while ((type = NBTTagType.fromId(is.readByte())) != NBTTagType.END) {
			String name = readPeNBTString(is, varint);
			switch (type) {
				case BYTE: {
					tag.setByte(name, is.readByte());
					break;
				}
				case SHORT: {
					tag.setShort(name, is.readShortLE());
					break;
				}
				case INT: {
					tag.setInt(name, readPeNBTSInt(is, varint));
					break;
				}
				case LONG: {
					tag.setLong(name, is.readLongLE());
					break;
				}
				case FLOAT: {
					tag.setFloat(name, is.readFloatLE());
					break;
				}
				case DOUBLE: {
					tag.setDouble(name, is.readDoubleLE());
					break;
				}
				case BYTE_ARRAY: {
					byte[] array = new byte[readPeNBTInt(is, varint)];
					for(int i = 0; i < array.length; i++) {
						array[i] = is.readByte();
					}
					tag.setByteArray(name, array);
					break;
				}
				case INT_ARRAY: {
					int[] array = new int[readPeNBTInt(is, varint)];
					for(int i = 0; i < array.length; i++) {
						array[i] = readPeNBTSInt(is, varint);
					}
					tag.setIntArray(name, array);
					break;
				}
				//Not in PE (Yet) :S
				/*case LONG_ARRAY: {
					long[] array = new long[is.readInt()];
					for (int j = 0; j < array.length; j++) {
						array[j] = is.readLong();
					}
					tag.setLongArray(name, array);
					break;
				}*/
				case STRING: {
					tag.setString(name, readPeNBTString(is, varint));
					break;
				}
				case COMPOUND: {
					NBTTagCompoundWrapper compound = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
					readPeCompoundPayload(is, varint, compound);
					tag.setCompound(name, compound);
					break;
				}
				case LIST: {
					NBTTagListWrapper list = ServerPlatform.get().getWrapperFactory().createEmptyNBTList();
					readPeListPayload(is, varint, list);
					tag.setList(name, list);
					break;
				}
				default: {
					throw new IOException(MessageFormat.format("Unknown or unsupported tag type {0}", type));
				}
			}
		}
	}

	private static void readPeListPayload(ByteBuf is, boolean varint, NBTTagListWrapper tag) throws IOException {
		NBTTagType type = NBTTagType.fromId(is.readByte());
		int size = readPeNBTSInt(is, varint);
		if ((type == NBTTagType.END) && (size > 0)) {
			throw new IOException("Missing type");
		}
		for (int i = 0; i < size; i++) {
			switch (type) {
				case BYTE: {
					tag.addByte(is.readByte());
					break;
				}
				case SHORT: {
					tag.addShort(is.readShortLE());
					break;
				}
				case INT: {
					tag.addInt(readPeNBTSInt(is, varint));
					break;
				}
				case LONG: {
					tag.addLong(is.readLongLE());
					break;
				}
				case FLOAT: {
					tag.addFloat(is.readFloatLE());
					break;
				}
				case DOUBLE: {
					tag.addDouble(is.readDoubleLE());
					break;
				}
				case BYTE_ARRAY: {
					byte[] array = new byte[readPeNBTInt(is, varint)];
					for(int ii = 0; ii < array.length; ii++) {
						array[ii] = is.readByte();
					}
					tag.addByteArray(array);
					break;
				}
				case INT_ARRAY: {
					int[] array = new int[readPeNBTInt(is, varint)];
					for(int ii = 0; ii < array.length; ii++) {
						array[ii] = readPeNBTSInt(is, varint);
					}
					tag.addIntArray(array);
					break;
				}
				//Not in PE (Yet) :S
				/*case LONG_ARRAY: {
					long[] array = new long[is.readInt()];
					for (int j = 0; j < array.length; j++) {
						array[j] = is.readLong();
					}
					tag.addLongArray(array);
					break;
				}*/
				case STRING: {
					tag.addString(readPeNBTString(is, varint));
					break;
				}
				case COMPOUND: {
					NBTTagCompoundWrapper compound = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
					readPeCompoundPayload(is, varint, compound);
					tag.addCompound(compound);
					break;
				}
				case LIST: {
					NBTTagListWrapper list = ServerPlatform.get().getWrapperFactory().createEmptyNBTList();
					readPeListPayload(is, varint, list);
					tag.addList(list);
					break;
				}
				default: {
					throw new IOException(MessageFormat.format("Unknown or unsupported tag type {0}", type));
				}
			}
		}
	}

	private static void writePeNBTInt(ByteBuf to, boolean varint, int i) {
		if (varint) {
			VarNumberSerializer.writeVarInt(to, i);
		} else {
			to.writeIntLE(i);
		}
	}

	private static void writePeNBTSInt(ByteBuf to, boolean varint, int i) {
		if (varint) {
			VarNumberSerializer.writeSVarInt(to, i);
		} else {
			to.writeIntLE(i);
		}
	}

	private static int readPeNBTInt(ByteBuf from, boolean varint) {
		if (varint) {
			return VarNumberSerializer.readVarInt(from);
		} else {
			return from.readIntLE();
		}
	}

	private static int readPeNBTSInt(ByteBuf from, boolean varint) {
		if (varint) {
			return VarNumberSerializer.readSVarInt(from);
		} else {
			return from.readIntLE();
		}
	}

	private static void writePeNBTString(ByteBuf to, boolean varint, String s) {
		if (varint) {
			byte[] data = s.getBytes(StandardCharsets.UTF_8);
			VarNumberSerializer.writeVarInt(to, data.length);
			to.writeBytes(data);
		} else {
			byte[] data = s.getBytes(StandardCharsets.UTF_8);
			to.writeShortLE(data.length);
			to.writeBytes(data);
		}
	}

	private static String readPeNBTString(ByteBuf from, boolean varint) {
		int length;
		if (varint) {
			length = VarNumberSerializer.readVarInt(from);
		} else {
			length = from.readShortLE();
		}
		return new String(MiscSerializer.readBytes(from, length), StandardCharsets.UTF_8);
	}

}
