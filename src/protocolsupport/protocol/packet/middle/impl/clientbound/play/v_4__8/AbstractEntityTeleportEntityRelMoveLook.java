package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__8;

import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__20.AbstractLocationOffsetEntityRelMoveLook;
import protocolsupport.protocol.types.networkentity.NetworkEntityDataCache;

public abstract class AbstractEntityTeleportEntityRelMoveLook extends AbstractLocationOffsetEntityRelMoveLook {

	protected AbstractEntityTeleportEntityRelMoveLook(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		NetworkEntityDataCache ecache = entity.getDataCache();

		double x = ecache.getX();
		double y = ecache.getY();
		double z = ecache.getZ();
		if (entityLegacyLocationOffset != null) {
			x += entityLegacyLocationOffset.getX();
			y += entityLegacyLocationOffset.getY();
			z += entityLegacyLocationOffset.getZ();
		}
		writeTeleport(x, y, z, yaw, pitch, onGround);
	}

	protected abstract void writeTeleport(double x, double y, double z, byte yaw, byte pitch, boolean onGround);

}
