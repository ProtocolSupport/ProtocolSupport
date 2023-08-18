package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__20;

import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleEntityLook;
import protocolsupport.protocol.typeremapper.entity.NetworkEntityTransformHelper;
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyFormatRegistry;
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyFormatRegistry.NetworkEntityLegacyFormatTable;
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyLocationOffsetRegistry;
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyLocationOffsetRegistry.NetworkEntityLegacyLocationOffsetTable;
import protocolsupport.protocol.typeremapper.entity.legacy.NetworkEntityLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.entity.legacy.NetworkEntityLegacyDataRegistry.NetworkEntityLegacyDataTable;

public abstract class AbstractLocationOffsetEntityLook extends MiddleEntityLook {

	protected AbstractLocationOffsetEntityLook(IMiddlePacketInit init) {
		super(init);
	}

	protected final NetworkEntityLegacyDataTable entityLegacyDataTable = NetworkEntityLegacyDataRegistry.INSTANCE.getTable(version);
	protected final NetworkEntityLegacyFormatTable entityLegacyFormatTable = NetworkEntityLegacyFormatRegistry.INSTANCE.getTable(version);
	protected final NetworkEntityLegacyLocationOffsetTable entityLegacyLocationOffsetTable = NetworkEntityLegacyLocationOffsetRegistry.INSTANCE.getTable(version);

	@Override
	protected void handle() {
		super.handle();

		NetworkEntityLegacyLocationOffsetRegistry.LocationOffset offset = entityLegacyLocationOffsetTable.get(NetworkEntityTransformHelper.transformTypeFormat(entity.getType(), entityLegacyDataTable, entityLegacyFormatTable));
		if (offset != null) {
			yaw += offset.getYaw();
			pitch += offset.getPitch();
		}
	}

}
