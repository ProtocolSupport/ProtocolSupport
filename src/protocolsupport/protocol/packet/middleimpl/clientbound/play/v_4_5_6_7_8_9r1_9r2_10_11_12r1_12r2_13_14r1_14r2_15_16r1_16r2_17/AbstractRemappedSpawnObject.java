package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17;

import protocolsupport.protocol.packet.middle.CancelMiddlePacketException;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnObject;
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

public abstract class AbstractRemappedSpawnObject extends MiddleSpawnObject {

	protected AbstractRemappedSpawnObject(MiddlePacketInit init) {
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
			throw CancelMiddlePacketException.INSTANCE;
		}

		super.handle();

		lType = lLType;
		lObjectdata = entityLegacyObjectdataDataTable.get(lLType).applyAsInt(objectdata);
		fType = entityLegacyFormatTable.get(lLType).getType();
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
