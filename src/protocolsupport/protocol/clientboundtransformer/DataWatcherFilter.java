package protocolsupport.protocol.clientboundtransformer;

import io.netty.buffer.Unpooled;

import java.util.ArrayList;

import net.minecraft.server.v1_8_R1.BlockPosition;
import net.minecraft.server.v1_8_R1.Entity;
import net.minecraft.server.v1_8_R1.EntityAgeable;
import net.minecraft.server.v1_8_R1.EntityEnderman;
import net.minecraft.server.v1_8_R1.EntityMinecartAbstract;
import net.minecraft.server.v1_8_R1.ItemStack;
import net.minecraft.server.v1_8_R1.Vector3f;
import protocolsupport.protocol.PacketDataSerializer;

public class DataWatcherFilter {

	public static byte[] filterEntityLivingData(int version, Entity entity, byte[] data) {
		if (entity instanceof EntityAgeable) {
			ArrayList<DataWatcherObject> objects = decodeData(version, data);
			for (DataWatcherObject object : objects) {
				if (object.key == 12) {
					object.value = ((int) ((byte) object.value));
					object.type = 2;
					return encodeData(version, objects);
				}
			}
		} else if (entity instanceof EntityEnderman) {
			ArrayList<DataWatcherObject> objects = decodeData(version, data);
			for (DataWatcherObject object : objects) {
				if (object.key == 16) {
					object.value = ((byte) ((short) object.value));
					object.type = 0;
					return encodeData(version, objects);
				}
			}
		} else if (entity instanceof EntityMinecartAbstract) {
			ArrayList<DataWatcherObject> objects = decodeData(version, data);
			for (DataWatcherObject object : objects) {
				if (object.key == 20) {
					int value = (int) object.value;
					int p1 = value & 0xFFFF;
					int p2 = value >> 12;
					object.value = (p2 << 16) | p1;
					return encodeData(version, objects);
				}
			}
		}
		return data;
	}

	private static ArrayList<DataWatcherObject> decodeData(int version, byte[] data) {
		ArrayList<DataWatcherObject> arraylist = new ArrayList<DataWatcherObject>();
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
            		arraylist.add(new DataWatcherObject(type, key, serializer.readByte()));
            		break;
            	}
            	case 1: {
            		arraylist.add(new DataWatcherObject(type, key, serializer.readShort()));
            		break;
            	}
            	case 2: {
            		arraylist.add(new DataWatcherObject(type, key, serializer.readInt()));
            		break;
            	}
            	case 3: {
            		arraylist.add(new DataWatcherObject(type, key, serializer.readFloat()));
            		break;
            	}
				case 4: {
					arraylist.add(new DataWatcherObject(type, key, serializer.readString(32767)));
					break;
				}
				case 5: {
					arraylist.add(new DataWatcherObject(type, key, serializer.i()));
					break;
				}
				case 6: {
					final int x = serializer.readInt();
					final int y = serializer.readInt();
					final int z = serializer.readInt();
					arraylist.add(new DataWatcherObject(type, key, new BlockPosition(x, y, z)));
					break;
				}
				case 7: {
					final float x = serializer.readFloat();
					final float y = serializer.readFloat();
					final float z = serializer.readFloat();
					arraylist.add(new DataWatcherObject(type, key, new Vector3f(x, y, z)));
					break;
				}
            }
		} while (true);
		return arraylist;
	}

	private static byte[] encodeData(int version, ArrayList<DataWatcherObject> objects) {
		PacketDataSerializer serializer = new PacketDataSerializer(Unpooled.buffer(), version);
		for (DataWatcherObject object : objects) {
	        final int tk = (object.type << 5 | (object.key & 0x1F)) & 0xFF;
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
					serializer.a((ItemStack) object.value);
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

	private static class DataWatcherObject {

		private int type;
		private int key;
		private Object value;
		public DataWatcherObject(int type, int key, Object value) {
			this.type = type;
			this.key = key;
			this.value = value;
		}

		@Override
		public String toString() {
			return "type: "+type+", key: "+key+", value: "+value;
		}
	}

}
