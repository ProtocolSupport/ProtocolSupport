package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnObject;
import protocolsupport.protocol.typeremapper.basic.ObjectDataRemappersRegistry;
import protocolsupport.protocol.typeremapper.basic.ObjectDataRemappersRegistry.ObjectDataRemappingTable;
import protocolsupport.protocol.typeremapper.entity.EntityRemappersRegistry;
import protocolsupport.protocol.typeremapper.entity.EntityRemappersRegistry.EntityRemappingTable;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;

public abstract class AbstractRemappedSpawnObject extends MiddleSpawnObject {

	protected final EntityRemappingTable entityRemappingTable = EntityRemappersRegistry.REGISTRY.getTable(version);
	protected final ObjectDataRemappingTable entityObjectDataRemappingTable = ObjectDataRemappersRegistry.REGISTRY.getTable(version);

	public AbstractRemappedSpawnObject(ConnectionImpl connection) {
		super(connection);
	}

	protected NetworkEntityType rType;
	protected int rObjectdata;

	@Override
	protected void handleReadData() {
		super.handleReadData();

		rType = entityRemappingTable.getRemap(entity.getType()).getLeft();
		rObjectdata = entityObjectDataRemappingTable.getRemap(rType).applyAsInt(objectdata);
	}

}
