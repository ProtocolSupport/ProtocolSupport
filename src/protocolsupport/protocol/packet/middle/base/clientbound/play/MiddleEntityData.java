package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.MiddlePacketCancelException;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;
import protocolsupport.protocol.storage.netcache.NetworkEntityCache;
import protocolsupport.protocol.types.networkentity.NetworkEntity;

public abstract class MiddleEntityData extends ClientBoundMiddlePacket {

	protected MiddleEntityData(IMiddlePacketInit init) {
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
			throw MiddlePacketCancelException.INSTANCE;
		}

		decodeDataLast(serverdata);
	}

	protected int decodeEntityId(ByteBuf serverdata) {
		return VarNumberCodec.readVarInt(serverdata);
	}

	protected abstract void decodeData(ByteBuf serverdata);

	protected void decodeDataLast(ByteBuf serverdata) {
	}

}
