package protocolsupport.protocol.utils.types;

import protocolsupport.utils.Utils;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

import java.util.UUID;

import org.bukkit.util.Vector;

public class NetworkEntity {

	public static NetworkEntity createMob(UUID uuid, int id, int typeId) {
		return new NetworkEntity(uuid, id, NetworkEntityType.getMobByNetworkTypeId(typeId));
	}

	public static NetworkEntity createObject(UUID uuid, int id, int typeId, int objectData) {
		return new NetworkEntity(uuid, id, NetworkEntityType.getObjectByNetworkTypeIdAndData(typeId, objectData));
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

	private final DataCache cache = new DataCache();

	public DataCache getDataCache() {
		return cache;
	}

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

	public static class DataCache {

		private byte baseFlags = 0;
		private boolean firstMeta = true;

		public byte getBaseFlags() {
			return baseFlags;
		}

		public boolean getBaseFlag(int bitpos) {
			return (baseFlags & (1 << (bitpos - 1))) != 0;
		}

		public void setBaseFlag(int bitpos, boolean value) {
			setBaseFlag(bitpos, value ? 1 : 0);
		}

		public void setBaseFlag(int bitpos, int value) {
			baseFlags &= ~(1 << (bitpos - 1));
			baseFlags |= (value << (bitpos - 1));
		}

		public void setBaseFlags(byte baseFlags) {
			this.baseFlags = baseFlags;
		}

		public boolean isFirstMeta() {
			return firstMeta;
		}

		public void setFirstMeta(boolean firstMeta) {
			this.firstMeta = firstMeta;
		}

		public boolean isFirstMeta() {
			return firstMeta;
		}

		public void setFirstMeta(boolean firstMeta) {
			this.firstMeta = firstMeta;
		}

		//Cache for PE shizzles.
		private long peBaseFlags = 0;
		private float sizeModifier = 1f;
		private int attachedId = -1;
		private Byte headRotation = null;
		private int vehicleId = 0;
		private Vector riderPosition = null;
		private Float rotationlock = null;
		private float maxHealth = 20f;
		private int strength = 0;
		private ItemStackWrapper helmet = ItemStackWrapper.NULL;
		private ItemStackWrapper chestplate = ItemStackWrapper.NULL;
		private ItemStackWrapper leggings = ItemStackWrapper.NULL;
		private ItemStackWrapper boots = ItemStackWrapper.NULL;
		private ItemStackWrapper hand = ItemStackWrapper.NULL;
		private ItemStackWrapper offhand = ItemStackWrapper.NULL;
		
		public long getPeBaseFlags() {
			return peBaseFlags;
		}

		public float getSizeModifier() {
			return sizeModifier;
		}

		public void setSizeModifier(float sizeModifier) {
			this.sizeModifier = sizeModifier;
		}

		public int getAttachedId() {
			return attachedId;
		}

		public void setAttachedId(int attachedId) {
			this.attachedId = attachedId;
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
		
		public void setHand(ItemStackWrapper hand) {
			this.hand = hand;
		}

		public ItemStackWrapper getHand() { return this.hand; }
		
		public void setOffHand(ItemStackWrapper offhand) {
			this.offhand = offhand;
		}

		public ItemStackWrapper getOffhand() { return this.offhand; }
		
		public void setHeadRotation(byte headRot) {
			this.headRotation = headRot;
		}
		
		public byte getHeadRotation(byte normalRotation) {
			if (headRotation != null) {
				return headRotation;
			}
			return normalRotation;
		}
		
		public int getVehicleId() {
			return vehicleId;
		}
		
		public boolean isRiding() {
			return vehicleId != 0;
		}

		public void setVehicleId(int vehicleId) {
			this.vehicleId = vehicleId;
		}

		public Vector getRiderPosition() {
			return riderPosition;
		}

		public void setRiderPosition(Vector riderPosition) {
			this.riderPosition = riderPosition;
		}

		public Float getRotationlock() {
			return rotationlock;
		}

		public void setRotationlock(Float rotationlock) {
			this.rotationlock = rotationlock;
		}

		public float getMaxHealth() {
			return maxHealth;
		}

		public void setMaxHealth(float maxHealth) {
			this.maxHealth = maxHealth;
		}

		public int getStrength() {
			return strength;
		}

		public void setStrength(int strength) {
			this.strength = strength;
		}

		@Override
		public String toString() {
			return Utils.toStringAllFields(this);
		}

	}

}
