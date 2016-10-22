package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.UUID;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.protocol.typeremapper.watchedentity.types.WatchedObject;

public abstract class MiddleSpawnObject<T> extends ClientBoundMiddlePacket<T> {

	protected int entityId;
	protected UUID uuid;
	protected int type;
	protected double x;
	protected double y;
	protected double z;
	protected int pitch;
	protected int yaw;
	protected int objectdata;
	protected int motX;
	protected int motY;
	protected int motZ;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		entityId = serializer.readVarInt();
		uuid = serializer.readUUID();
		type = serializer.readUnsignedByte();
		x = serializer.readDouble();
		y = serializer.readDouble();
		z = serializer.readDouble();
		pitch = serializer.readUnsignedByte();
		yaw = serializer.readUnsignedByte();
		objectdata = serializer.readInt();
		motX = serializer.readShort();
		motY = serializer.readShort();
		motZ = serializer.readShort();
	}

	@Override
	public void handle() {
		cache.addWatchedEntity(new WatchedObject(entityId, type));
	}

}
