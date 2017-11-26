package protocolsupport.protocol.utils.minecraftdata;

import java.io.BufferedReader;
import java.util.EnumMap;
import java.util.Map;

import org.bukkit.util.Vector;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import protocolsupport.protocol.utils.types.NetworkEntityType;
import protocolsupport.utils.Utils;

public class PocketData {
	
	private final static Gson gson = new Gson();

	public static JsonObject getFileObject(String name) {
		return new JsonParser().parse(getResource(name)).getAsJsonObject();
	}
	
	public static BufferedReader getResource(String name) {
		return Utils.getResource("pe/" + name);
	}
	
	//Extra entity data such as BoundingBoxes, RiderPositions and Offsets necessary for Pocket Edition.
	private final static Map<NetworkEntityType, PocketEntityData> entityDatas = new EnumMap<NetworkEntityType, PocketEntityData>(NetworkEntityType.class);
	
	public static void readEntityDatas() {
		getFileObject("entitydata.json").entrySet().forEach(entry -> {
			entityDatas.put(NetworkEntityType.valueOf(entry.getKey()), gson.fromJson(entry.getValue(), PocketEntityData.class));
		});
	}

	public static PocketEntityData getPocketEntityData(NetworkEntityType type) {
		return entityDatas.get(type);
	}
	
	public static class PocketEntityData {
		private PocketBoundingBox BoundingBox;
		private PocketOffset Offset;
		private PocketRiderInfo RiderInfo;
		
		public PocketBoundingBox getBoundingBox() {
			return BoundingBox;
		}
		
		public PocketOffset getOffset() {
			return Offset;
		}
		
		public PocketRiderInfo getRiderInfo() {
			return RiderInfo;
		}
		
		public static class PocketBoundingBox {
			private float width;
			private float height;
			
			public float getWidth() {
				return width;
			}
			
			public float getHeight() {
				return height;
			}
		}
		
		public static class PocketOffset {
			private float x;
			private float y;
			private float z;
			private byte yaw;
			private byte pitch;
			
			public float getX() {
				return x;
			}
			
			public float getY() {
				return y;
			}
			
			public float getZ() {
				return z;
			}
			
			public byte getYaw() {
				return yaw;
			}
			
			public byte getPitch() {
				return pitch;
			}
		}
		
		public static class PocketRiderInfo {
			private double x;
			private double y;
			private double z;
			private Float rotationlock = null; //Optional
			private int vehicleId = -1; //Used only in meta
			
			public Vector getPosition() {
				return new Vector(x, y + 1.2, z);
			}
			
			public Float getRotationLock() {
				return rotationlock;
			}
			
			public int getVehicleId() {
				return vehicleId;
			}

			public void setVehicleId(int vehicleId) {
				this.vehicleId = vehicleId;
			}

			public void setPosition(Vector position) {
				this.x = position.getX();
				this.y = position.getY() - 1.2;
				this.z = position.getZ();
			}
		}
		
	}
}
