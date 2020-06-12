package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer.NetworkEntityMetadataList;
import protocolsupport.protocol.typeremapper.entity.EntityRemappersRegistry;
import protocolsupport.protocol.typeremapper.entity.EntityRemappersRegistry.EntityRemappingTable;
import protocolsupport.protocol.typeremapper.entity.metadata.object.NetworkEntityMetadataObjectRemapper;

public abstract class AbstractRemappedEntityMetadata extends AbstractKnownEntityMetadata {

	public AbstractRemappedEntityMetadata(ConnectionImpl connection) {
		super(connection);
	}

	protected final EntityRemappingTable entityRemapTable = EntityRemappersRegistry.REGISTRY.getTable(version);

	protected final NetworkEntityMetadataList rMetadata = new NetworkEntityMetadataList();

	@Override
	protected void handleReadData() {
		super.handleReadData();

		for (NetworkEntityMetadataObjectRemapper remapper : entityRemapTable.getRemap(entity.getType()).getRight()) {
			remapper.remap(entity, metadata, rMetadata);
		}
		entity.getDataCache().unsetFirstMeta();
	}

	@Override
	protected void cleanup() {
		super.cleanup();
		rMetadata.clear();
	}

}
