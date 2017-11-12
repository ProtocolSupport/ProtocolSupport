package protocolsupport.protocol.utils.minecraftdata;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
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
			private List<Float> position = new ArrayList<Float>(); 
			private List<Byte> rotation = new ArrayList<Byte>();
			
			public Float getX() {
				return position.get(0);
			}
			
			public Float getY() {
				return position.get(1);
			}
			
			public Float getZ() {
				return position.get(2);
			}
			
			public Byte getYaw() {
				return rotation.get(0);
			}
			
			public Byte getPitch() {
				return rotation.get(1);
			}
		}
		
		public static class PocketRiderInfo {
			private List<Double> position = new ArrayList<Double>();
			private Float rotationlock = null;
			
			public Vector getPosition() {
				return new Vector(position.get(0), position.get(1) + 1, position.get(2));
			}
			
			public Float getRotationLock() {
				return rotationlock;
			}
			
			public void setPosition(Vector position) {
				this.position.set(0, position.getX());
				this.position.set(1, position.getY() - 1d);
				this.position.set(2, position.getZ());
			}
		}
	}
}
