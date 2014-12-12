package protocolsupport.protocol.clientboundtransformer;

import java.util.Iterator;

import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import io.netty.buffer.Unpooled;
import net.minecraft.server.v1_8_R1.BlockPosition;
import net.minecraft.server.v1_8_R1.Entity;
import net.minecraft.server.v1_8_R1.EntityAgeable;
import net.minecraft.server.v1_8_R1.EntityArmorStand;
import net.minecraft.server.v1_8_R1.EntityEnderman;
import net.minecraft.server.v1_8_R1.EntityItemFrame;
import net.minecraft.server.v1_8_R1.EntityMinecartAbstract;
import net.minecraft.server.v1_8_R1.ItemStack;
import net.minecraft.server.v1_8_R1.Vector3f;
import protocolsupport.protocol.PacketDataSerializer;

public class DataWatcherFilter {

	public static byte[] filterEntityData(int version, Entity entity, byte[] data) {
		TIntObjectMap<DataWatcherObject> objects = decodeData(version, data);
		if (entity instanceof EntityAgeable) {
			DataWatcherObject object = objects.get(12);
			if (object != null) {
				object.value = ((int) ((byte) object.value));
				object.type = 2;
			}
		} else if (entity instanceof EntityEnderman) {
			DataWatcherObject object = objects.get(16);
			if (object != null) {
				object.value = ((byte) ((short) object.value));
				object.type = 0;
			}
		} else if (entity instanceof EntityMinecartAbstract) {
			DataWatcherObject object = objects.get(20);
			if (object != null) {
				int value = (int) object.value;
				int p1 = value & 0xFFFF;
				int p2 = value >> 12;
				object.value = (p2 << 16) | p1;
			}
		} else if (entity instanceof EntityItemFrame) {
			if (objects.containsKey(8)) {
				ItemStack item = (ItemStack) objects.get(8).value;
				objects.put(2, new DataWatcherObject(5, item));
			}
			if (objects.containsKey(9)) {
				int rotation = (byte) objects.get(9).value;
				objects.put(3, new DataWatcherObject(0, ((byte) (rotation >> 1))));
			}
		} else if (entity instanceof EntityArmorStand) { //replace with entity ender crystal data
			objects.clear();
			objects.put(8, new DataWatcherObject(2, 5));
		}
		//remove type 7 watched objects
		Iterator<DataWatcherObject> iterator = objects.valueCollection().iterator();
		while (iterator.hasNext()) {
			if (iterator.next().type == 7) {
				iterator.remove();
			}
		}
		//add object in case objects list is empty
		if (objects.isEmpty()) {
			objects.put(0, new DataWatcherObject(0, (byte) 0));
		}
		return encodeData(version, objects);
	}

	private static TIntObjectMap<DataWatcherObject> decodeData(int version, byte[] data) {
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
					map.put(key, new DataWatcherObject(type, serializer.i()));
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

	private static byte[] encodeData(int version, TIntObjectMap<DataWatcherObject> objects) {
		PacketDataSerializer serializer = new PacketDataSerializer(Unpooled.buffer(), version);
		TIntObjectIterator<DataWatcherObject> iterator = objects.iterator();
		while (iterator.hasNext()) {
			iterator.advance();
			DataWatcherObject object = iterator.value();
	        final int tk = (object.type << 5 | (iterator.key() & 0x1F)) & 0xFF;
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
		private Object value;
		public DataWatcherObject(int type, Object value) {
			this.type = type;
			this.value = value;
		}

	}

}
