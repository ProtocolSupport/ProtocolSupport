package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__20;

import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleEntityRelMoveLook;
import protocolsupport.protocol.typeremapper.entity.NetworkEntityTransformHelper;
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyFormatRegistry;
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyFormatRegistry.NetworkEntityLegacyFormatTable;
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyLocationOffsetRegistry;
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyLocationOffsetRegistry.NetworkEntityLegacyLocationOffsetTable;
import protocolsupport.protocol.typeremapper.entity.legacy.NetworkEntityLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.entity.legacy.NetworkEntityLegacyDataRegistry.NetworkEntityLegacyDataTable;

public abstract class AbstractLocationOffsetEntityRelMoveLook extends MiddleEntityRelMoveLook {

	protected AbstractLocationOffsetEntityRelMoveLook(IMiddlePacketInit init) {
		super(init);
	}

	protected final NetworkEntityLegacyDataTable entityLegacyDataTable = NetworkEntityLegacyDataRegistry.INSTANCE.getTable(version);
	protected final NetworkEntityLegacyFormatTable entityLegacyFormatTable = NetworkEntityLegacyFormatRegistry.INSTANCE.getTable(version);
	protected final NetworkEntityLegacyLocationOffsetTable entityLegacyLocationOffsetTable = NetworkEntityLegacyLocationOffsetRegistry.INSTANCE.getTable(version);

	protected NetworkEntityLegacyLocationOffsetRegistry.LocationOffset entityLegacyLocationOffset;

	@Override
	protected void handle() {
		entityLegacyLocationOffset = entityLegacyLocationOffsetTable.get(NetworkEntityTransformHelper.transformTypeFormat(entity.getType(), entityLegacyDataTable, entityLegacyFormatTable));
		if (entityLegacyLocationOffset != null) {
			yaw += entityLegacyLocationOffset.getYaw();
			pitch += entityLegacyLocationOffset.getPitch();
		}
	}

}
