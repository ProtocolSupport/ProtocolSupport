package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.packet.middle.CancelMiddlePacketException;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnObject;
import protocolsupport.protocol.typeremapper.basic.ObjectDataRemappersRegistry;
import protocolsupport.protocol.typeremapper.basic.ObjectDataRemappersRegistry.ObjectDataRemappingTable;
import protocolsupport.protocol.typeremapper.entity.NetworkEntityLegacyLocationOffset;
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyFormatRegistry;
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyFormatRegistry.NetworkEntityLegacyFormatTable;
import protocolsupport.protocol.typeremapper.entity.legacy.NetworkEntityLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.entity.legacy.NetworkEntityLegacyDataRegistry.NetworkEntityLegacyDataTable;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;

public abstract class AbstractRemappedSpawnObject extends MiddleSpawnObject {

	public AbstractRemappedSpawnObject(MiddlePacketInit init) {
		super(init);
	}

	protected final NetworkEntityLegacyDataTable legacyEntityEntryTable = NetworkEntityLegacyDataRegistry.INSTANCE.getTable(version);
	protected final NetworkEntityLegacyFormatTable entityDataFormatTable = NetworkEntityLegacyFormatRegistry.INSTANCE.getTable(version);
	protected final ObjectDataRemappingTable entityObjectDataRemapTable = ObjectDataRemappersRegistry.REGISTRY.getTable(version);
	protected final NetworkEntityLegacyLocationOffset entityOffset = NetworkEntityLegacyLocationOffset.get(version);

	protected NetworkEntityType lType;
	protected NetworkEntityType fType;
	protected int rObjectdata;

	@Override
	protected void handle() {
		NetworkEntityType lLType = legacyEntityEntryTable.get(entity.getType()).getType();

		if (lLType == NetworkEntityType.NONE) {
			throw CancelMiddlePacketException.INSTANCE;
		}

		super.handle();

		lType = lLType;
		fType = entityDataFormatTable.get(lLType).getType();
		rObjectdata = entityObjectDataRemapTable.getRemap(lLType).applyAsInt(objectdata);
		NetworkEntityLegacyLocationOffset.Offset offset = entityOffset.get(lLType);
		if (offset != null) {
			x += offset.getX();
			y += offset.getY();
			z += offset.getZ();
			yaw += offset.getYaw();
			pitch += offset.getPitch();
		}
	}

}
