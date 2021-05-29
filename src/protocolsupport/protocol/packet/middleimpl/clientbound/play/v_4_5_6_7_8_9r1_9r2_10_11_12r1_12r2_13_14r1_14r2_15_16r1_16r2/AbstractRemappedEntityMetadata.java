package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer.NetworkEntityMetadataList;
import protocolsupport.protocol.typeremapper.entity.NetworkEntityTransformHelper;
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyFormatRegistry;
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyFormatRegistry.NetworkEntityLegacyFormatTable;
import protocolsupport.protocol.typeremapper.entity.legacy.NetworkEntityLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.entity.legacy.NetworkEntityLegacyDataRegistry.NetworkEntityLegacyDataEntry;
import protocolsupport.protocol.typeremapper.entity.legacy.NetworkEntityLegacyDataRegistry.NetworkEntityLegacyDataTable;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;

public abstract class AbstractRemappedEntityMetadata extends AbstractKnownEntityMetadata {

	protected AbstractRemappedEntityMetadata(MiddlePacketInit init) {
		super(init);
	}

	protected final NetworkEntityLegacyDataTable entityLegacyDataTable = NetworkEntityLegacyDataRegistry.INSTANCE.getTable(version);
	protected final NetworkEntityLegacyFormatTable entityLegacyFormatTable = NetworkEntityLegacyFormatRegistry.INSTANCE.getTable(version);

	protected NetworkEntityType lType;
	protected final NetworkEntityMetadataList fMetadata = new NetworkEntityMetadataList();

	@Override
	protected void handle() {
		super.handle();

		NetworkEntityLegacyDataEntry legacyEntityEntry = entityLegacyDataTable.get(entity.getType());
		lType = legacyEntityEntry.getType();
		NetworkEntityTransformHelper.transformMetadata(entity, metadata, legacyEntityEntry, entityLegacyFormatTable, fMetadata);
	}

	@Override
	protected void cleanup() {
		super.cleanup();
		fMetadata.clear();
	}

}
