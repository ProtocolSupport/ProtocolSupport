package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityRelMoveLook;
import protocolsupport.protocol.types.networkentity.NetworkEntityDataCache;

public class EntityRelMoveLook extends MiddleEntityRelMoveLook {

	public EntityRelMoveLook(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		NetworkEntityDataCache ecache = entity.getDataCache();

		codec.write(EntityTeleport.create(entityId, ecache.getX(), ecache.getY(), ecache.getZ(), ecache.getYaw(), ecache.getPitch()));
	}

}
