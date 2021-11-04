package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8;

import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleEntityRelMove;
import protocolsupport.protocol.typeremapper.entity.NetworkEntityTransformHelper;
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyFormatRegistry;
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyFormatRegistry.NetworkEntityLegacyFormatTable;
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyLocationOffsetRegistry;
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyLocationOffsetRegistry.NetworkEntityLegacyLocationOffsetTable;
import protocolsupport.protocol.typeremapper.entity.legacy.NetworkEntityLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.entity.legacy.NetworkEntityLegacyDataRegistry.NetworkEntityLegacyDataTable;
import protocolsupport.protocol.types.networkentity.NetworkEntityDataCache;

public abstract class AbstractEntityTeleportEntityRelMove extends MiddleEntityRelMove {

	protected AbstractEntityTeleportEntityRelMove(IMiddlePacketInit init) {
		super(init);
	}

	protected final NetworkEntityLegacyDataTable entityLegacyDataTable = NetworkEntityLegacyDataRegistry.INSTANCE.getTable(version);
	protected final NetworkEntityLegacyFormatTable entityLegacyFormatTable = NetworkEntityLegacyFormatRegistry.INSTANCE.getTable(version);
	protected final NetworkEntityLegacyLocationOffsetTable entityLegacyLocationOffsetTable = NetworkEntityLegacyLocationOffsetRegistry.INSTANCE.getTable(version);

	@Override
	protected void write() {
		NetworkEntityDataCache ecache = entity.getDataCache();

		double x = ecache.getX();
		double y = ecache.getY();
		double z = ecache.getZ();
		byte yaw = ecache.getYawB();
		byte pitch = ecache.getPitchB();
		NetworkEntityLegacyLocationOffsetRegistry.LocationOffset entityOffsetEntry = entityLegacyLocationOffsetTable.get(NetworkEntityTransformHelper.transformTypeFormat(entity.getType(), entityLegacyDataTable, entityLegacyFormatTable));
		if (entityOffsetEntry != null) {
			x += entityOffsetEntry.getX();
			y += entityOffsetEntry.getY();
			z += entityOffsetEntry.getZ();
			yaw += entityOffsetEntry.getYaw();
			pitch += entityOffsetEntry.getPitch();
		}
		writeTeleport(x, y, z, yaw, pitch, onGround);
	}

	protected abstract void writeTeleport(double x, double y, double z, byte yaw, byte pitch, boolean onGround);

}
