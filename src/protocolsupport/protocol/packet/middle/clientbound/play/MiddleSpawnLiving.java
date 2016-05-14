package protocolsupport.protocol.packet.middle.clientbound.play;

import java.io.IOException;
import java.util.UUID;

import gnu.trove.map.TIntObjectMap;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.protocol.typeremapper.watchedentity.types.WatchedEntity;
import protocolsupport.protocol.typeremapper.watchedentity.types.WatchedLiving;
import protocolsupport.protocol.utils.datawatcher.DataWatcherDeserializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public abstract class MiddleSpawnLiving<T> extends ClientBoundMiddlePacket<T> {

	protected int entityId;
	protected UUID uuid;
	protected int type;
	protected double x;
	protected double y;
	protected double z;
	protected int yaw;
	protected int pitch;
	protected int headPitch;
	protected int motX;
	protected int motY;
	protected int motZ;
	protected WatchedEntity wentity;
	protected TIntObjectMap<DataWatcherObject<?>> metadata;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		entityId = serializer.readVarInt();
		uuid = serializer.readUUID();
		type = serializer.readUnsignedByte();
		x = serializer.readDouble();
		y = serializer.readDouble();
		z = serializer.readDouble();
		yaw = serializer.readUnsignedByte();
		pitch = serializer.readUnsignedByte();
		headPitch = serializer.readUnsignedByte();
		motX = serializer.readShort();
		motY = serializer.readShort();
		motZ = serializer.readShort();
		metadata = DataWatcherDeserializer.decodeData(serializer);
	}

	@Override
	public void handle() {
		wentity = new WatchedLiving(entityId, type);
		storage.addWatchedEntity(wentity);
	}

}
