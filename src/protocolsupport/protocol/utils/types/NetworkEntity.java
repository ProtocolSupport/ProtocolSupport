package protocolsupport.protocol.utils.types;

import java.util.UUID;

import org.bukkit.util.Vector;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.utils.Utils;

public class NetworkEntity {

	public static NetworkEntity createMob(UUID uuid, int id, int typeId) {
		return new NetworkEntity(uuid, id, NetworkEntityType.getMobByTypeId(typeId));
	}

	public static NetworkEntity createObject(UUID uuid, int id, int typeId, int objectData) {
		return new NetworkEntity(uuid, id, NetworkEntityType.getObjectByTypeAndData(typeId, objectData));
	}

	public static NetworkEntity createPlayer(UUID uuid, int id) {
		return new NetworkEntity(uuid, id, NetworkEntityType.PLAYER);
	}

	public static NetworkEntity createPlayer(int id) {
		return createPlayer(null, id);
	}

	private final UUID uuid;
	private final int id;
	private final NetworkEntityType type;

	public NetworkEntity(UUID uuid, int id, NetworkEntityType type) {
		this.uuid = uuid;
		this.id = id;
		this.type = type;
	}

	public UUID getUUID() {
		return uuid;
	}

	public int getId() {
		return id;
	}

	public NetworkEntityType getType() {
		return type;
	}
	
	public boolean isOfType(NetworkEntityType type) {
		return type.isOfType(type);
	}

	private DataCache cache = new DataCache();

	public DataCache getDataCache() {
		return cache;
	}
	
	public void updateDataCache(DataCache updateWith) {
		cache = updateWith;
	}
	
	public void updateMetadata(TIntObjectMap<DataWatcherObject<?>> updateWith) {
		cache.updateMeta(updateWith);
	}
	

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

	public class DataCache {
		public boolean firstMeta = true;
		public TIntObjectMap<DataWatcherObject<?>> metadata = new TIntObjectHashMap<>();
		public int attachedId = -1; //Leashed? Data is send in pocket meta, but might be useful to store for other things.
		public boolean inLove = false; //Show just one metaupdate?
		public Rider rider = new Rider();
		
		public Object getMetaValue(int index) {
			return metadata.get(index).getValue();
		}
		
		public boolean getMetaBool(int index) {
			if(metadata.containsKey(index)) {
				try {
					return (boolean) getMetaValue(index);
				} catch (ClassCastException e) {}
			}
			return false;
		}
		
		public boolean getMetaBool(int index, int bitpos) {
			System.out.println("Calling check metabool on index, " + index);
			if(metadata.containsKey(index) /*&& (getMetaValue(index) instanceof Byte)*/) {
				System.out.println("Found, in actuallity the value is of type, " + metadata.get(index).getValue().getClass().getName());
				return (((byte) getMetaValue(index)) & (1 << (bitpos - 1))) != 0;
			}
			return false;
		}
		
		public void updateMeta(TIntObjectMap<DataWatcherObject<?>> updateWith) {
			for(int index : updateWith.keys()) {
				metadata.put(index, updateWith.get(index));
			}
		}
		
		public class Rider {
			public boolean riding = false;
			public Vector position = new Vector();
			public boolean rotationLocked = false;
			public float rotationMin = 0;
			public float rotationMax = 0;
		};
		
		@Override
		public String toString() {
			return Utils.toStringAllFields(this);
		}
	}

}
