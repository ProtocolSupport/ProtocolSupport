package com.github.shevchik.protocolsupport.utils;

import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import io.netty.buffer.Unpooled;

import java.io.IOException;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Vector3f;
import com.github.shevchik.protocolsupport.api.ProtocolVersion;
import com.github.shevchik.protocolsupport.protocol.PacketDataSerializer;
import com.github.shevchik.protocolsupport.utils.DataWatcherSerializer.DataWatcherObject.ValueType;

public class DataWatcherSerializer {

	public static TIntObjectMap<DataWatcherObject> decodeData(ProtocolVersion version, byte[] data) throws IOException {
		TIntObjectMap<DataWatcherObject> map = new TIntObjectHashMap<DataWatcherObject>(10, 0.5f, -1);
		PacketDataSerializer serializer = new PacketDataSerializer(Unpooled.wrappedBuffer(data), version);
		do {
			final int b0 = serializer.readUnsignedByte();
			if (b0 == 127) {
				break;
			}
			final ValueType type = ValueType.fromId((b0 & 0xE0) >> 5);
			final int key = b0 & 0x1F;
			switch (type) {
				case BYTE: {
					map.put(key, new DataWatcherObject(type, serializer.readByte()));
					break;
				}
				case SHORT: {
					map.put(key, new DataWatcherObject(type, serializer.readShort()));
					break;
				}
				case INT: {
					map.put(key, new DataWatcherObject(type, serializer.readInt()));
					break;
				}
				case FLOAT: {
					map.put(key, new DataWatcherObject(type, serializer.readFloat()));
					break;
				}
				case STRING: {
					map.put(key, new DataWatcherObject(type, serializer.readString(32767)));
					break;
				}
				case ITEMSTACK: {
					map.put(key, new DataWatcherObject(type, serializer.readItemStack()));
					break;
				}
				case VECTOR3I: {
					final int x = serializer.readInt();
					final int y = serializer.readInt();
					final int z = serializer.readInt();
					map.put(key, new DataWatcherObject(type, new BlockPosition(x, y, z)));
					break;
				}
				case VECTOR3F: {
					final float x = serializer.readFloat();
					final float y = serializer.readFloat();
					final float z = serializer.readFloat();
					map.put(key, new DataWatcherObject(type, new Vector3f(x, y, z)));
					break;
				}
			}
		} while (true);
		return map;
	}

	public static byte[] encodeData(ProtocolVersion version, TIntObjectMap<DataWatcherObject> objects) {
		PacketDataSerializer serializer = new PacketDataSerializer(Allocator.allocateBuffer(), version);
		try {
			TIntObjectIterator<DataWatcherObject> iterator = objects.iterator();
			while (iterator.hasNext()) {
				iterator.advance();
				DataWatcherObject object = iterator.value();
				final int tk = ((object.type.getId() << 5) | (iterator.key() & 0x1F)) & 0xFF;
				serializer.writeByte(tk);
				switch (object.type) {
					case BYTE: {
						serializer.writeByte((byte) object.value);
						break;
					}
					case SHORT: {
						serializer.writeShort((short) object.value);
						break;
					}
					case INT: {
						serializer.writeInt((int) object.value);
						break;
					}
					case FLOAT: {
						serializer.writeFloat((float) object.value);
						break;
					}
					case STRING: {
						serializer.writeString((String) object.value);
						break;
					}
					case ITEMSTACK: {
						serializer.writeItemStack((ItemStack) object.value);
						break;
					}
					case VECTOR3I: {
						BlockPosition blockPos = (BlockPosition) object.value;
						serializer.writeInt(blockPos.getX());
						serializer.writeInt(blockPos.getY());
						serializer.writeInt(blockPos.getZ());
						break;
					}
					case VECTOR3F: {
						Vector3f vector = (Vector3f) object.value;
						serializer.writeFloat(vector.getX());
						serializer.writeFloat(vector.getY());
						serializer.writeFloat(vector.getZ());
						break;
					}
				}
			}
			serializer.writeByte(127);
			return Utils.toArray(serializer);
		} finally {
			serializer.release();
		}
	}

	public static class DataWatcherObject {

		public ValueType type;
		public Object value;

		public DataWatcherObject(ValueType type, Object value) {
			this.type = type;
			this.value = value;
		}

		public void toByte() {
			type = ValueType.BYTE;
			value = ((Number) value).byteValue();
		}

		public void toShort() {
			type = ValueType.SHORT;
			value = ((Number) value).shortValue();
		}

		public void toInt() {
			type = ValueType.INT;
			value = ((Number) value).intValue();
		}

		public void toFloat() {
			type = ValueType.FLOAT;
			value = ((Number) value).floatValue();
		}

		@Override
		public String toString() {
			return
				new StringBuilder()
				.append("type: ").append(type).append(" ")
				.append("value: ").append(value)
				.toString();
		}

		public static enum ValueType {
			BYTE, SHORT, INT, FLOAT, STRING, ITEMSTACK, VECTOR3I, VECTOR3F;

			public int getId() {
				return ordinal();
			}

			public static ValueType fromId(int id) {
				return values()[id];
			}
		}

	}

}
