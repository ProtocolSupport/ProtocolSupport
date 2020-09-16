package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer.NetworkEntityMetadataList;
import protocolsupport.protocol.typeremapper.entity.LegacyNetworkEntityRegistry;
import protocolsupport.protocol.typeremapper.entity.LegacyNetworkEntityRegistry.LegacyNetworkEntityEntry;
import protocolsupport.protocol.typeremapper.entity.LegacyNetworkEntityRegistry.LegacyNetworkEntityTable;
import protocolsupport.protocol.typeremapper.entity.NetworkEntityDataFormatTransformRegistry;
import protocolsupport.protocol.typeremapper.entity.NetworkEntityDataFormatTransformRegistry.NetworkEntityDataFormatTransformerTable;
import protocolsupport.protocol.typeremapper.entity.NetworkEntityTransformHelper;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;

public abstract class AbstractRemappedEntityMetadata extends AbstractKnownEntityMetadata {

	public AbstractRemappedEntityMetadata(MiddlePacketInit init) {
		super(init);
	}

	protected final LegacyNetworkEntityTable legacyEntityEntryTable = LegacyNetworkEntityRegistry.INSTANCE.getTable(version);
	protected final NetworkEntityDataFormatTransformerTable entityDataFormatTable = NetworkEntityDataFormatTransformRegistry.INSTANCE.getTable(version);

	protected NetworkEntityType lType;
	protected final NetworkEntityMetadataList fMetadata = new NetworkEntityMetadataList();

	@Override
	protected void handleReadData() {
		super.handleReadData();

		LegacyNetworkEntityEntry legacyEntityEntry = legacyEntityEntryTable.get(entity.getType());
		lType = legacyEntityEntry.getType();
		NetworkEntityTransformHelper.transformMetadata(entity, metadata, legacyEntityEntry, entityDataFormatTable, fMetadata);
	}

	@Override
	protected void cleanup() {
		super.cleanup();
		fMetadata.clear();
	}

}
