package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.types.networkentity.NetworkEntity;

public abstract class MiddleEntityDestroy extends MiddleEntityData {

	protected MiddleEntityDestroy(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void decodeData(ByteBuf serverdata) {
	}

	@Override
	protected NetworkEntity getEntityInstance(int entityId) {
		return entityCache.removeEntity(entityId);
	}

}
