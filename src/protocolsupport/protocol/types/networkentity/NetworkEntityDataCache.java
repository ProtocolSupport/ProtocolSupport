package protocolsupport.protocol.types.networkentity;

import org.bukkit.util.Vector;

import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.utils.Utils;

public class NetworkEntityDataCache {

	protected byte baseFlags = 0;
	protected boolean firstMeta = true;
	protected long peBaseFlags = 0;
	protected float sizeModifier = 1f;
	protected int attachedId = -1;
	protected byte headRotation = 0;
	protected boolean headRotationSet = false;
	protected int vehicleId = 0;
	protected float maxHealth = 20f;
	protected float posX = 0;
	protected float posY = 0;
	protected float posZ = 0;
	protected byte yaw = 0;
	protected byte pitch = 0;
	protected Vector riderPosition = null;
	protected Float rotationlock = null;
	protected Equipment equipment = null;

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

	public void setHeadRotation(byte headRot) {
		this.headRotation = headRot;
		this.headRotationSet = true;
	}

	public byte getHeadRotation(byte normalRotation) {
		if (headRotationSet) {
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

	public Float getRotationLock() {
		return rotationlock;
	}

	public void setRotationLock(Float rotationlock) {
		this.rotationlock = rotationlock;
	}

	public float getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(float maxHealth) {
		this.maxHealth = maxHealth;
	}

	public float getPosX() {
		return posX;
	}

	public float getPosY() {
		return posY;
	}

	public float getPosZ() {
		return posZ;
	}

	public void setPos(float x, float y, float z) {
		this.posX = x;
		this.posY = y;
		this.posZ = z;
	}

	public byte getYaw() {
		return yaw;
	}

	public void setYaw(byte yaw) {
		this.yaw = yaw;
	}

	public byte getPitch() {
		return pitch;
	}

	public void setPitch(byte pitch) {
		this.pitch = pitch;
	}

	public Equipment getEquipment() {
		if (equipment == null) {
			equipment = new Equipment();
		}
		return equipment;
	}

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

	public class Equipment {
		protected NetworkItemStack helmet = NetworkItemStack.NULL;
		protected NetworkItemStack chestplate = NetworkItemStack.NULL;
		protected NetworkItemStack leggings = NetworkItemStack.NULL;
		protected NetworkItemStack boots = NetworkItemStack.NULL;
		protected NetworkItemStack hand = NetworkItemStack.NULL;
		protected NetworkItemStack offhand = NetworkItemStack.NULL;

		public void setHelmet(NetworkItemStack helmet) {
			this.helmet = helmet;
		}

		public NetworkItemStack getHelmet() {
			return this.helmet;
		}

		public void setChestplate(NetworkItemStack chestplate) {
			this.chestplate = chestplate;
		}

		public NetworkItemStack getChestplate() {
			return this.chestplate;
		}

		public void setLeggings(NetworkItemStack leggings) {
			this.leggings = leggings;
		}

		public NetworkItemStack getLeggings() {
			return this.leggings;
		}

		public void setBoots(NetworkItemStack boots) {
			this.boots = boots;
		}

		public NetworkItemStack getBoots() {
			return this.boots;
		}

		public void setHand(NetworkItemStack hand) {
			this.hand = hand;
		}

		public NetworkItemStack getHand() {
			return this.hand;
		}

		public void setOffHand(NetworkItemStack offhand) {
			this.offhand = offhand;
		}

		public NetworkItemStack getOffhand() {
			return this.offhand;
		}
	}

}
