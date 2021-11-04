package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17r1_17r2;

import protocolsupport.protocol.codec.NetworkEntityMetadataSerializer.NetworkEntityMetadataList;
import protocolsupport.protocol.packet.middle.MiddlePacketCancelException;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleEntityMetadata;
import protocolsupport.protocol.typeremapper.entity.NetworkEntityTransformHelper;
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyFormatRegistry;
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyFormatRegistry.NetworkEntityLegacyFormatTable;
import protocolsupport.protocol.typeremapper.entity.legacy.NetworkEntityLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.entity.legacy.NetworkEntityLegacyDataRegistry.NetworkEntityLegacyDataEntry;
import protocolsupport.protocol.typeremapper.entity.legacy.NetworkEntityLegacyDataRegistry.NetworkEntityLegacyDataTable;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;

public abstract class AbstractRemappedEntityMetadata extends MiddleEntityMetadata {

	protected AbstractRemappedEntityMetadata(IMiddlePacketInit init) {
		super(init);
	}

	protected final NetworkEntityLegacyDataTable entityLegacyDataTable = NetworkEntityLegacyDataRegistry.INSTANCE.getTable(version);
	protected final NetworkEntityLegacyFormatTable entityLegacyFormatTable = NetworkEntityLegacyFormatRegistry.INSTANCE.getTable(version);

	protected NetworkEntityType lType;
	protected final NetworkEntityMetadataList fMetadata = new NetworkEntityMetadataList();

	@Override
	protected void handle() {
		NetworkEntityLegacyDataEntry legacyEntityEntry = entityLegacyDataTable.get(entity.getType());
		NetworkEntityType lLType = legacyEntityEntry.getType();

		if (lLType == NetworkEntityType.NONE) {
			throw MiddlePacketCancelException.INSTANCE;
		}

		lType = lLType;
		NetworkEntityTransformHelper.transformMetadata(entity, metadata, legacyEntityEntry, entityLegacyFormatTable, fMetadata);
	}

	@Override
	protected void cleanup() {
		super.cleanup();
		fMetadata.clear();
	}

}
