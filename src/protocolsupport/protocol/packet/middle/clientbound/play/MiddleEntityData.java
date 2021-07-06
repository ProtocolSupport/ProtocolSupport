package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.CancelMiddlePacketException;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.storage.netcache.NetworkEntityCache;
import protocolsupport.protocol.types.networkentity.NetworkEntity;

public abstract class MiddleEntityData extends ClientBoundMiddlePacket {

	protected MiddleEntityData(MiddlePacketInit init) {
		super(init);
	}

	protected final NetworkEntityCache entityCache = cache.getEntityCache();

	protected NetworkEntity entity;

	@Override
	protected void decode(ByteBuf serverdata) {
		int entityId = decodeEntityId(serverdata);
		decodeData(serverdata);
		entity = entityCache.getEntity(entityId);

		if (entity == null) {
			throw CancelMiddlePacketException.INSTANCE;
		}
	}

	protected int decodeEntityId(ByteBuf serverdata) {
		return VarNumberCodec.readVarInt(serverdata);
	}

	protected abstract void decodeData(ByteBuf serverdata);

}
