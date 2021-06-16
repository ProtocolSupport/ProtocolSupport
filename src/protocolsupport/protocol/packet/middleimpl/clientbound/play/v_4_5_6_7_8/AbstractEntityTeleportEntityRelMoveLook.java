package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8;

import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17.AbstractLocationOffsetEntityRelMoveLook;
import protocolsupport.protocol.types.networkentity.NetworkEntityDataCache;

public abstract class AbstractEntityTeleportEntityRelMoveLook extends AbstractLocationOffsetEntityRelMoveLook {

	protected AbstractEntityTeleportEntityRelMoveLook(MiddlePacketInit init) {
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
