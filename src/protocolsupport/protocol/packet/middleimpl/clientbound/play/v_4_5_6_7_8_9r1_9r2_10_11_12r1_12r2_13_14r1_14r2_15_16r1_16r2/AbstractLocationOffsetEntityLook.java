package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityLook;
import protocolsupport.protocol.typeremapper.entity.NetworkEntityLegacyLocationOffset;
import protocolsupport.protocol.typeremapper.entity.legacy.NetworkEntityLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.entity.legacy.NetworkEntityLegacyDataRegistry.NetworkEntityLegacyDataTable;

public abstract class AbstractLocationOffsetEntityLook extends MiddleEntityLook {

	protected AbstractLocationOffsetEntityLook(MiddlePacketInit init) {
		super(init);
	}

	protected final NetworkEntityLegacyDataTable legacyEntityEntryTable = NetworkEntityLegacyDataRegistry.INSTANCE.getTable(version);
	protected final NetworkEntityLegacyLocationOffset entityOffset = NetworkEntityLegacyLocationOffset.get(version);

	@Override
	protected void handle() {
		super.handle();

		NetworkEntityLegacyLocationOffset.Offset offset = entityOffset.get(legacyEntityEntryTable.get(entity.getType()).getType());
		if (offset != null) {
			yaw += offset.getYaw();
			pitch += offset.getPitch();
		}
	}

}
