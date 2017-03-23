package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.UUID;

import gnu.trove.map.TIntObjectMap;
import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.SpecificRemapper;
import protocolsupport.protocol.typeremapper.watchedentity.types.WatchedEntity;
import protocolsupport.protocol.typeremapper.watchedentity.types.WatchedLiving;
import protocolsupport.protocol.utils.datawatcher.DataWatcherDeserializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public abstract class MiddleSpawnLiving extends ClientBoundMiddlePacket {

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
	public void readFromServerData(ByteBuf serverdata) {
		entityId = VarNumberSerializer.readVarInt(serverdata);
		uuid = MiscSerializer.readUUID(serverdata);
		type = VarNumberSerializer.readVarInt(serverdata);
		x = serverdata.readDouble();
		y = serverdata.readDouble();
		z = serverdata.readDouble();
		yaw = serverdata.readUnsignedByte();
		pitch = serverdata.readUnsignedByte();
		headPitch = serverdata.readUnsignedByte();
		motX = serverdata.readShort();
		motY = serverdata.readShort();
		motZ = serverdata.readShort();
		metadata = DataWatcherDeserializer.decodeData(serverdata, ProtocolVersion.getLatest(ProtocolType.PC));
	}

	@Override
	public void handle() {
		wentity = new WatchedLiving(entityId, type);
		cache.addWatchedEntity(wentity);
	}

	@Override
	public boolean isValid() {
		return SpecificRemapper.getMobByTypeId(type) != SpecificRemapper.NONE;
	}

}
