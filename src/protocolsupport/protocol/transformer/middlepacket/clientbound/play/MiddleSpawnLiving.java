package protocolsupport.protocol.transformer.middlepacket.clientbound.play;

import java.io.IOException;

import gnu.trove.map.TIntObjectMap;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.ClientBoundMiddlePacket;
import protocolsupport.protocol.typeremapper.watchedentity.types.WatchedEntity;
import protocolsupport.protocol.typeremapper.watchedentity.types.WatchedLiving;
import protocolsupport.utils.DataWatcherObject;
import protocolsupport.utils.DataWatcherSerializer;
import protocolsupport.utils.netty.ChannelUtils;

public abstract class MiddleSpawnLiving<T> extends ClientBoundMiddlePacket<T> {

	protected int entityId;
	protected int type;
	protected int x;
	protected int y;
	protected int z;
	protected int yaw;
	protected int pitch;
	protected int headPitch;
	protected int motX;
	protected int motY;
	protected int motZ;
	protected WatchedEntity wentity;
	protected TIntObjectMap<DataWatcherObject> metadata;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) throws IOException {
		entityId = serializer.readVarInt();
		type = serializer.readUnsignedByte();
		x = serializer.readInt();
		y = serializer.readInt();
		z = serializer.readInt();
		yaw = serializer.readUnsignedByte();
		pitch = serializer.readUnsignedByte();
		headPitch = serializer.readUnsignedByte();
		motX = serializer.readShort();
		motY = serializer.readShort();
		motZ = serializer.readShort();
		metadata = DataWatcherSerializer.decodeData(ProtocolVersion.MINECRAFT_1_8, ChannelUtils.toArray(serializer));
	}

	@Override
	public void handle() {
		wentity = new WatchedLiving(entityId, type);
		storage.addWatchedEntity(wentity);
	}

}
