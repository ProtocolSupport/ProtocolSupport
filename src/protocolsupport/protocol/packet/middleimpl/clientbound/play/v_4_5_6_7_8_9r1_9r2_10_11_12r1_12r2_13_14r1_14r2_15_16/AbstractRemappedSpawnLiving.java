package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.CancelMiddlePacketException;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnLiving;
import protocolsupport.protocol.typeremapper.basic.GenericIdSkipper;
import protocolsupport.protocol.typeremapper.entity.EntityRemappersRegistry;
import protocolsupport.protocol.typeremapper.entity.EntityRemappersRegistry.EntityRemappingTable;
import protocolsupport.protocol.typeremapper.utils.SkippingTable.EnumSkippingTable;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;

public abstract class AbstractRemappedSpawnLiving extends MiddleSpawnLiving {

	public AbstractRemappedSpawnLiving(ConnectionImpl connection) {
		super(connection);
	}

	protected final EnumSkippingTable<NetworkEntityType> entitySkipTable = GenericIdSkipper.ENTITY.getTable(version);
	protected final EntityRemappingTable entityRemapTable = EntityRemappersRegistry.REGISTRY.getTable(version);

	protected NetworkEntityType rType;

	@Override
	protected void handleReadData() {
		if (entitySkipTable.shouldSkip(entity.getType())) {
			throw CancelMiddlePacketException.INSTANCE;
		}

		super.handleReadData();

		rType = entityRemapTable.getRemap(entity.getType()).getLeft();
	}

}
