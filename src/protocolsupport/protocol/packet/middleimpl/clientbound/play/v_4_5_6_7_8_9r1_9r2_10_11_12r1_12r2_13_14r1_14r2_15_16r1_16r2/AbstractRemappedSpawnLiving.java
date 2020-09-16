package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.packet.middle.CancelMiddlePacketException;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnLiving;
import protocolsupport.protocol.typeremapper.entity.LegacyNetworkEntityRegistry;
import protocolsupport.protocol.typeremapper.entity.LegacyNetworkEntityRegistry.LegacyNetworkEntityTable;
import protocolsupport.protocol.typeremapper.entity.NetworkEntityDataFormatTransformRegistry;
import protocolsupport.protocol.typeremapper.entity.NetworkEntityDataFormatTransformRegistry.NetworkEntityDataFormatTransformerTable;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;

public abstract class AbstractRemappedSpawnLiving extends MiddleSpawnLiving {

	public AbstractRemappedSpawnLiving(MiddlePacketInit init) {
		super(init);
	}

	protected final LegacyNetworkEntityTable legacyEntityEntryTable = LegacyNetworkEntityRegistry.INSTANCE.getTable(version);
	protected final NetworkEntityDataFormatTransformerTable entityDataFormatTable = NetworkEntityDataFormatTransformRegistry.INSTANCE.getTable(version);

	protected NetworkEntityType lType;
	protected NetworkEntityType fType;

	@Override
	protected void handleReadData() {
		NetworkEntityType lLType = legacyEntityEntryTable.get(entity.getType()).getType();

		if (lLType == NetworkEntityType.NONE) {
			throw CancelMiddlePacketException.INSTANCE;
		}

		super.handleReadData();

		lType = lLType;
		fType = entityDataFormatTable.get(lType).getKey();
	}

}
