package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityRelMoveLook;
import protocolsupport.protocol.typeremapper.entity.NetworkEntityLegacyLocationOffset;
import protocolsupport.protocol.typeremapper.entity.legacy.NetworkEntityLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.entity.legacy.NetworkEntityLegacyDataRegistry.NetworkEntityLegacyDataTable;

public abstract class AbstractLocationOffsetEntityRelMoveLook extends MiddleEntityRelMoveLook {

	protected AbstractLocationOffsetEntityRelMoveLook(MiddlePacketInit init) {
		super(init);
	}

	protected final NetworkEntityLegacyDataTable entityLegacyDataTable = NetworkEntityLegacyDataRegistry.INSTANCE.getTable(version);
	protected final NetworkEntityLegacyLocationOffset entityLegacyOffset = NetworkEntityLegacyLocationOffset.get(version);

	protected NetworkEntityLegacyLocationOffset.Offset entityLegacyOffsetEntry;

	@Override
	protected void handle() {
		super.handle();

		entityLegacyOffsetEntry = entityLegacyOffset.get(entityLegacyDataTable.get(entity.getType()).getType());
		if (entityLegacyOffsetEntry != null) {
			yaw += entityLegacyOffsetEntry.getYaw();
			pitch += entityLegacyOffsetEntry.getPitch();
		}
	}

}
