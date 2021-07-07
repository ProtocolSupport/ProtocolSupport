package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.UUIDCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.storage.netcache.NetworkEntityCache;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;

public abstract class MiddleSpawnPainting extends ClientBoundMiddlePacket {

	protected MiddleSpawnPainting(MiddlePacketInit init) {
		super(init);
	}

	protected final NetworkEntityCache entityCache = cache.getEntityCache();

	protected NetworkEntity entity;
	protected int type;
	protected final Position position = new Position(0, 0, 0);
	protected int direction;

	@Override
	protected void decode(ByteBuf serverdata) {
		int entityId = VarNumberCodec.readVarInt(serverdata);
		UUID uuid = UUIDCodec.readUUID2L(serverdata);
		entity = new NetworkEntity(uuid, entityId, NetworkEntityType.PAINTING);
		type = VarNumberCodec.readVarInt(serverdata);
		PositionCodec.readPosition(serverdata, position);
		direction = serverdata.readUnsignedByte();

		entityCache.addEntity(entity);
	}

}
