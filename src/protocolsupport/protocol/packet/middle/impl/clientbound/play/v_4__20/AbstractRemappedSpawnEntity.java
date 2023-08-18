package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__20;

import protocolsupport.protocol.packet.middle.MiddlePacketCancelException;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleSpawnEntity;
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyFormatRegistry;
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyFormatRegistry.NetworkEntityLegacyFormatTable;
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyLocationOffsetRegistry;
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyLocationOffsetRegistry.NetworkEntityLegacyLocationOffsetTable;
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyObjectdataFormatRegistry;
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyObjectdataFormatRegistry.NetworkEntityLegacyObjectdataFormatTable;
import protocolsupport.protocol.typeremapper.entity.legacy.NetworkEntityLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.entity.legacy.NetworkEntityLegacyDataRegistry.NetworkEntityLegacyDataTable;
import protocolsupport.protocol.typeremapper.entity.legacy.NetworkEntityLegacyObjectdataDataRegistry;
import protocolsupport.protocol.typeremapper.entity.legacy.NetworkEntityLegacyObjectdataDataRegistry.NetworkEntityLegacyObjectdataDataTable;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;

public abstract class AbstractRemappedSpawnEntity extends MiddleSpawnEntity {

	protected AbstractRemappedSpawnEntity(IMiddlePacketInit init) {
		super(init);
	}

	protected final NetworkEntityLegacyDataTable entityLegacyDataTable = NetworkEntityLegacyDataRegistry.INSTANCE.getTable(version);
	protected final NetworkEntityLegacyObjectdataDataTable entityLegacyObjectdataDataTable = NetworkEntityLegacyObjectdataDataRegistry.INSTANCE.getTable(version);

	protected final NetworkEntityLegacyFormatTable entityLegacyFormatTable = NetworkEntityLegacyFormatRegistry.INSTANCE.getTable(version);
	protected final NetworkEntityLegacyObjectdataFormatTable entityLegacyObjectdataFormatTable = NetworkEntityLegacyObjectdataFormatRegistry.INSTANCE.getTable(version);
	protected final NetworkEntityLegacyLocationOffsetTable entityLegacyLocationOffsetTable = NetworkEntityLegacyLocationOffsetRegistry.INSTANCE.getTable(version);

	protected NetworkEntityType lType;
	protected int lObjectdata;

	protected NetworkEntityType fType;
	protected int fObjectdata;

	@Override
	protected void handle() {
		NetworkEntityType lLType = entityLegacyDataTable.get(entity.getType()).getType();

		if (lLType == NetworkEntityType.NONE) {
			throw MiddlePacketCancelException.INSTANCE;
		}

		lType = lLType;
		lObjectdata = entityLegacyObjectdataDataTable.get(lLType).applyAsInt(objectdata);
		fType = entityLegacyFormatTable.get(lType).getType();
		fObjectdata = entityLegacyObjectdataFormatTable.get(fType).applyAsInt(lObjectdata);
		NetworkEntityLegacyLocationOffsetRegistry.LocationOffset offset = entityLegacyLocationOffsetTable.get(fType);
		if (offset != null) {
			x += offset.getX();
			y += offset.getY();
			z += offset.getZ();
			yaw += offset.getYaw();
			pitch += offset.getPitch();
		}
	}

}
