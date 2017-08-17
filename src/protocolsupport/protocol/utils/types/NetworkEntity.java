package protocolsupport.protocol.utils.types;

import org.bukkit.util.Vector;
import protocolsupport.utils.Utils;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

import java.util.UUID;

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
	
	public boolean isOfType(NetworkEntityType typeToCheck) {
		return type.isOfType(typeToCheck);
	}

	private DataCache cache = new DataCache();

	public DataCache getDataCache() {
		return cache;
	}
	
	public void updateDataCache(DataCache updateWith) {
		this.cache = updateWith;
	}
	
	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

	public static class DataCache {
		private byte pcBaseFlags = 0;
		public boolean firstMeta = false;
		
		public byte getPcBaseFlags() {
			return pcBaseFlags;
		}
		
		public boolean getPcBaseFlag(int bitpos) {
			return (pcBaseFlags & (1 << (bitpos - 1))) != 0;
		}
		
		public void setPcBaseFlag(int bitpos, boolean value) {
			setPcBaseFlag(bitpos, value ? 1 : 0);
		}
		
		public void setPcBaseFlag(int bitpos, int value) {
			pcBaseFlags &= ~(1 << (bitpos - 1));
			pcBaseFlags |= (value << (bitpos - 1));
		}
		
		public void setPcBaseFlags(byte pcBaseFlags) {
			this.pcBaseFlags = pcBaseFlags;
		}
		
		//Cache for PE shizzles.
		private long peBaseFlags = 0;
		public int attachedId = -1; //Leashed? Data is send in pocket meta, but might be useful to store for other things.
		public Rider rider = new Rider(false);
		// static final null ItemStack, while the null ItemStack in Vanilla code is a static final variable, ProtocolSupport wraps
		// it inside a ItemStackWrapper, causing object duplication, so, to avoid too much memory usage, why not just reuse the
		// same object? After all, the nullItemStack isn't going to be edited by anyone anyway :)
		private static final ItemStackWrapper nullItemStack = ServerPlatform.get().getWrapperFactory().createNullItemStack();
		private ItemStackWrapper helmet = nullItemStack;
		private ItemStackWrapper chestplate = nullItemStack;
		private ItemStackWrapper leggings = nullItemStack;
		private ItemStackWrapper boots = nullItemStack;

		public long getPeBaseFlags() {
			return peBaseFlags;
		}
		
		public boolean getPeBaseFlag(int bitpos) {
			return (peBaseFlags & (1 << (bitpos - 1))) != 0;
		}
		
		public void setPeBaseFlag(int bitpos, boolean value) {
			setPeBaseFlag(bitpos, value ? 1 : 0);
		}
		
		public void setPeBaseFlag(int bitpos, long value) {
			peBaseFlags &= ~(1l << (bitpos - 1));
			peBaseFlags |= (value << (bitpos - 1));
		}
		
		public void setPeBaseFlags(long peBaseFlags) {
			this.peBaseFlags = peBaseFlags;
		}

		public void setHelmet(ItemStackWrapper helmet) {
			this.helmet = helmet;
		}

		public ItemStackWrapper getHelmet() { return this.helmet; }

		public void setChestplate(ItemStackWrapper chestplate) { 
			this.chestplate = chestplate; 
		}

		public ItemStackWrapper getChestplate() { return this.chestplate; }

		public void setLeggings(ItemStackWrapper leggings) {
			this.leggings = leggings;
		}

		public ItemStackWrapper getLeggings() { return this.leggings; }

		public void setBoots(ItemStackWrapper boots) {
			this.boots = boots;
		}

		public ItemStackWrapper getBoots() { return this.boots; }

		public class Rider {
			public boolean riding = false;
			public Vector position = new Vector(0, 0.6, 0);
			public boolean rotationLocked = false;
			public Float rotationMin;
			public Float rotationMax;
			
			public Rider(boolean riding) {
				this.riding = riding;
			}
			
			public Rider(Vector position, boolean rotationLocked, float rotationMax, float rotationMin) {
				this(true, position, rotationLocked, rotationMax, rotationMin);
			}
			
			public Rider(Vector position, boolean rotationLocked) {
				this(true, position, rotationLocked, null, null);
			}
			
			public Rider(boolean riding, Vector position, boolean rotationLocked, Float rotationMax, Float rotationMin) {
				this.riding = riding;
				this.position = position;
				this.rotationLocked = rotationLocked;
				this.rotationMax = rotationMax;
				this.rotationMin = rotationMin;
			}
		};
		
		@Override
		public String toString() {
			return Utils.toStringAllFields(this);
		}

	}

}
