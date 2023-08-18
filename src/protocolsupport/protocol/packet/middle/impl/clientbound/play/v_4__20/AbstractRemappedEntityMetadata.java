package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__20;

import protocolsupport.protocol.codec.NetworkEntityMetadataCodec.NetworkEntityMetadataList;
import protocolsupport.protocol.packet.middle.MiddlePacketCancelException;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleEntityMetadata;
import protocolsupport.protocol.typeremapper.entity.NetworkEntityTransformHelper;
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyFormatRegistry;
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyFormatRegistry.NetworkEntityLegacyFormatEntry;
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
	protected NetworkEntityType fType;

	protected final NetworkEntityMetadataList fMetadata = new NetworkEntityMetadataList();

	@Override
	protected void handle() {
		NetworkEntityLegacyDataEntry legacyDataEntry = entityLegacyDataTable.get(entity.getType());
		NetworkEntityType lLType = legacyDataEntry.getType();

		if (lLType == NetworkEntityType.NONE) {
			throw MiddlePacketCancelException.INSTANCE;
		}

		NetworkEntityLegacyFormatEntry legacyFormatEntry = entityLegacyFormatTable.get(lLType);

		lType = lLType;
		fType = legacyDataEntry.getType();

		NetworkEntityTransformHelper.transformMetadata(entity, metadata, legacyDataEntry, legacyFormatEntry, fMetadata);
	}

	@Override
	protected void cleanup() {
		super.cleanup();
		fMetadata.clear();
	}

}
