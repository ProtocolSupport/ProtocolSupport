package protocolsupport.utils;

import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import io.netty.buffer.Unpooled;

import java.io.IOException;

import net.minecraft.server.v1_8_R2.BlockPosition;
import net.minecraft.server.v1_8_R2.ItemStack;
import net.minecraft.server.v1_8_R2.Vector3f;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;

public class DataWatcherSerializer {

	public static TIntObjectMap<DataWatcherObject> decodeData(ProtocolVersion version, byte[] data) throws IOException {
		TIntObjectMap<DataWatcherObject> map = new TIntObjectHashMap<DataWatcherObject>(10, 0.5f, -1);
		PacketDataSerializer serializer = new PacketDataSerializer(Unpooled.wrappedBuffer(data), version);
		do {
			final int b0 = serializer.readUnsignedByte();
			if (b0 == 127) {
				break;
			}
			final int type = (b0 & 0xE0) >> 5;
			final int key = b0 & 0x1F;
			switch (type) {
				case 0: {
					map.put(key, new DataWatcherObject(type, serializer.readByte()));
					break;
				}
				case 1: {
					map.put(key, new DataWatcherObject(type, serializer.readShort()));
					break;
				}
				case 2: {
					map.put(key, new DataWatcherObject(type, serializer.readInt()));
					break;
				}
				case 3: {
					map.put(key, new DataWatcherObject(type, serializer.readFloat()));
					break;
				}
				case 4: {
					map.put(key, new DataWatcherObject(type, serializer.readString(32767)));
					break;
				}
				case 5: {
					map.put(key, new DataWatcherObject(type, serializer.readItemStack()));
					break;
				}
				case 6: {
					final int x = serializer.readInt();
					final int y = serializer.readInt();
					final int z = serializer.readInt();
					map.put(key, new DataWatcherObject(type, new BlockPosition(x, y, z)));
					break;
				}
				case 7: {
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
		PacketDataSerializer serializer = new PacketDataSerializer(Unpooled.buffer(), version);
		TIntObjectIterator<DataWatcherObject> iterator = objects.iterator();
		while (iterator.hasNext()) {
			iterator.advance();
			DataWatcherObject object = iterator.value();
			final int tk = ((object.type << 5) | (iterator.key() & 0x1F)) & 0xFF;
			serializer.writeByte(tk);
			switch (object.type) {
				case 0: {
					serializer.writeByte((byte) object.value);
					break;
				}
				case 1: {
					serializer.writeShort((short) object.value);
					break;
				}
				case 2: {
					serializer.writeInt((int) object.value);
					break;
				}
				case 3: {
					serializer.writeFloat((float) object.value);
					break;
				}
				case 4: {
					serializer.writeString((String) object.value);
					break;
				}
				case 5: {
					serializer.writeItemStack((ItemStack) object.value);
					break;
				}
				case 6: {
					BlockPosition blockPos = (BlockPosition) object.value;
					serializer.writeInt(blockPos.getX());
					serializer.writeInt(blockPos.getY());
					serializer.writeInt(blockPos.getZ());
					break;
				}
				case 7: {
					Vector3f vector = (Vector3f) object.value;
					serializer.writeFloat(vector.getX());
					serializer.writeFloat(vector.getY());
					serializer.writeFloat(vector.getZ());
					break;
				}
			}
		}
		serializer.writeByte(127);
		return serializer.readBytes(serializer.readableBytes()).array();
	}

	public static class DataWatcherObject {

		public int type;
		public Object value;

		public DataWatcherObject(int type, Object value) {
			this.type = type;
			this.value = value;
		}

		@Override
		public String toString() {
			return
				new StringBuilder()
				.append("type: ").append(type).append(" ")
				.append("value: ").append(value)
				.toString();
		}

	}

}
