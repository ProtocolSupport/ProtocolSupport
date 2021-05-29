package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.packet.middle.CancelMiddlePacketException;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnLiving;
import protocolsupport.protocol.typeremapper.entity.NetworkEntityLegacyLocationOffset;
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyFormatRegistry;
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyFormatRegistry.NetworkEntityLegacyFormatTable;
import protocolsupport.protocol.typeremapper.entity.legacy.NetworkEntityLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.entity.legacy.NetworkEntityLegacyDataRegistry.NetworkEntityLegacyDataTable;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;

public abstract class AbstractRemappedSpawnLiving extends MiddleSpawnLiving {

	protected AbstractRemappedSpawnLiving(MiddlePacketInit init) {
		super(init);
	}

	protected final NetworkEntityLegacyDataTable entityLegacyDataTable = NetworkEntityLegacyDataRegistry.INSTANCE.getTable(version);
	protected final NetworkEntityLegacyFormatTable entityLegacyFormatTable = NetworkEntityLegacyFormatRegistry.INSTANCE.getTable(version);
	protected final NetworkEntityLegacyLocationOffset entityLegacyOffset = NetworkEntityLegacyLocationOffset.get(version);

	protected NetworkEntityType lType;
	protected NetworkEntityType fType;

	@Override
	protected void handle() {
		NetworkEntityType lLType = entityLegacyDataTable.get(entity.getType()).getType();

		if (lLType == NetworkEntityType.NONE) {
			throw CancelMiddlePacketException.INSTANCE;
		}

		super.handle();

		lType = lLType;
		fType = entityLegacyFormatTable.get(lType).getType();
		NetworkEntityLegacyLocationOffset.Offset offset = entityLegacyOffset.get(lLType);
		if (offset != null) {
			x += offset.getX();
			y += offset.getY();
			z += offset.getZ();
			yaw += offset.getYaw();
			pitch += offset.getPitch();
		}
	}

}
