package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityRelMove;
import protocolsupport.protocol.types.networkentity.NetworkEntityDataCache;

public class EntityRelMove extends MiddleEntityRelMove {

	public EntityRelMove(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeToClient() {
		NetworkEntityDataCache ecache = entity.getDataCache();

		codec.write(EntityTeleport.create(entityId, ecache.getX(), ecache.getY(), ecache.getZ(), ecache.getYawB(), ecache.getPitchB(), onGround));
	}

}
