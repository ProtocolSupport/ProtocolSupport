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

	private final DataCache cache = new DataCache();

	public DataCache getDataCache() {
		return cache;
	}

	private Vector position;
	private Byte headYaw;
	private Byte yaw;
	private Byte pitch;
	private Boolean onGround = true;
	
	
	public void updatePosition(Vector updateWith) {
		position = updateWith;
	}
	
	public void updateRelPosition(Vector updateWith) {
		position = position.add(updateWith);
		System.out.println("Rel pos: " + position.toString());
	}
	
	public Vector getPosition() {
		return position;
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
	
	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

	public static class DataCache {
		public boolean firstMeta = true;
		public byte baseMetaFlags;
		@Override
		public String toString() {
			return Utils.toStringAllFields(this);
		}
	}

}
