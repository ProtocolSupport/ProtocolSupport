package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.UUIDSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.NetworkEntityCache;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;

public abstract class MiddleSpawnPainting extends ClientBoundMiddlePacket {

	protected final NetworkEntityCache entityCache = cache.getEntityCache();

	public MiddleSpawnPainting(ConnectionImpl connection) {
		super(connection);
	}

	protected NetworkEntity entity;
	protected int type;
	protected final Position position = new Position(0, 0, 0);
	protected int direction;

	@Override
	protected void readServerData(ByteBuf serverdata) {
		int entityId = VarNumberSerializer.readVarInt(serverdata);
		UUID uuid = UUIDSerializer.readUUID2L(serverdata);
		entity = new NetworkEntity(uuid, entityId, NetworkEntityType.PAINTING);
		type = VarNumberSerializer.readVarInt(serverdata);
		PositionSerializer.readPositionTo(serverdata, position);
		direction = serverdata.readUnsignedByte();
	}

	@Override
	public void handleReadData() {
		entityCache.addEntity(entity);
	}

}
