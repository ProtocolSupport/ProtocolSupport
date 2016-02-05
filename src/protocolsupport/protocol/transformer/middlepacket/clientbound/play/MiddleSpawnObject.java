package protocolsupport.protocol.transformer.middlepacket.clientbound.play;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.ClientBoundMiddlePacket;
import protocolsupport.protocol.typeremapper.watchedentity.types.WatchedObject;

public abstract class MiddleSpawnObject<T> extends ClientBoundMiddlePacket<T> {

	protected int entityId;
	protected int type;
	protected int x;
	protected int y;
	protected int z;
	protected int pitch;
	protected int yaw;
	protected int objectdata;
	protected int motX;
	protected int motY;
	protected int motZ;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) throws IOException {
		entityId = serializer.readVarInt();
		type = serializer.readUnsignedByte();
		x = serializer.readInt();
		y = serializer.readInt();
		z = serializer.readInt();
		pitch = serializer.readUnsignedByte();
		yaw = serializer.readUnsignedByte();
		objectdata = serializer.readInt();
		if (objectdata > 0) {
			motX = serializer.readShort();
			motY = serializer.readShort();
			motZ = serializer.readShort();
		}
	}

	@Override
	public void handle() {
		storage.addWatchedEntity(new WatchedObject(entityId, type));
	}

}
