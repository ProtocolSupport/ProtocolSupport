package protocolsupport.protocol.utils.types;

import java.util.UUID;

import org.bukkit.util.Vector;

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
	
	public boolean isOfType(NetworkEntityType checkWith) {
		return type.isOfType(checkWith);
	}

	private final DataCache data = new DataCache();

	public DataCache getDataCache() {
		return data;
	}

	//Movement
	private Vector position;
	private Vector velocity = new Vector(0, 0, 0);
	private Byte headYaw;
	private Byte yaw;
	private Byte pitch;
	private Boolean onGround = true;
	
	
	public void updatePosition(Vector updateWith) {
		position = updateWith;
	}
	
	public void updatePosition(Position updateWith) {
		position = new Vector(updateWith.getX(), updateWith.getY(), updateWith.getZ());
	}
	
	public void updateRelPosition(int relX, int relY, int relZ) {
		//X and Y are known. Relative is send (a * 32 - x * 32) * 128 = y                   y / 128 = a32 - x32         (y/128) + x32 = a 32               ((y / 128) + 32x) / 32 = a
		position = new Vector(((relX / 128) + (position.getX() * 32)) / 32, ((relY / 128) + (position.getY() * 32)) / 32, ((relZ / 128) + (position.getZ() * 32)) / 32);
	}
	
	public void addPosition(Vector toAdd) {
		position.add(toAdd);
	}
	
	public Vector getPosition() {
		return position;
	}
	
	public void updateVelocity(Vector updateWith) {
		velocity = updateWith;
	}
	
	public Vector getVelocity() {
		return velocity;
	}
	
	public boolean hasVelocity() {
		return velocity.getX() > 0.01 && velocity.getY() > 0.01 && velocity.getZ() > 0.01;
	}
	
	public void updateRotation(byte updateYaw, byte updatePitch) {
		yaw = updateYaw;
		pitch = updatePitch;
	}
	
	public void updateHeadYaw(byte updateWith) {
		headYaw = updateWith;
	}
	
	public byte getHeadYaw() {
		if(headYaw == null) return yaw;
		return headYaw;
	}
	
	public byte getYaw() {
		return yaw;
	}
	
	public byte getPitch() {
		return pitch;
	}
	
	public void updateOnGround(boolean updateWith) {
		onGround = updateWith;
	}
	
	public boolean getOnGround() {
		return onGround;
	}
	
	//DataCache
	public static class DataCache {
		public boolean firstMeta = true;
		public byte baseMetaFlags;
		@Override
		public String toString() {
			return Utils.toStringAllFields(this);
		}
	}
	
	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

}
