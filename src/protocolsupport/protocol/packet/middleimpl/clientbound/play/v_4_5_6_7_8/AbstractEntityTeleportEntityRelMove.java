package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityRelMove;
import protocolsupport.protocol.typeremapper.entity.NetworkEntityLegacyLocationOffset;
import protocolsupport.protocol.typeremapper.entity.legacy.NetworkEntityLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.entity.legacy.NetworkEntityLegacyDataRegistry.NetworkEntityLegacyDataTable;
import protocolsupport.protocol.types.networkentity.NetworkEntityDataCache;

public abstract class AbstractEntityTeleportEntityRelMove extends MiddleEntityRelMove {

	public AbstractEntityTeleportEntityRelMove(MiddlePacketInit init) {
		super(init);
	}

	protected final NetworkEntityLegacyDataTable legacyEntityEntryTable = NetworkEntityLegacyDataRegistry.INSTANCE.getTable(version);
	protected final NetworkEntityLegacyLocationOffset entityOffset = NetworkEntityLegacyLocationOffset.get(version);

	@Override
	protected void write() {
		NetworkEntityDataCache ecache = entity.getDataCache();

		double x = ecache.getX();
		double y = ecache.getY();
		double z = ecache.getZ();
		byte yaw = ecache.getYawB();
		byte pitch = ecache.getPitchB();
		NetworkEntityLegacyLocationOffset.Offset entityOffsetEntry = entityOffset.get(legacyEntityEntryTable.get(entity.getType()).getType());
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
