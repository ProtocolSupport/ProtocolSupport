package protocolsupport.protocol.typeremapper.entity.legacy.metadata;

import java.util.function.Consumer;

import protocolsupport.protocol.typeremapper.utils.MappingTable.IdMappingTable;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectBlockData;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class GenericEntityDirectBlockDataMetadataTransformer implements Consumer<ArrayMap<NetworkEntityMetadataObject<?>>> {

	protected final IdMappingTable blockMappingTable;
	protected final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBlockData> index;

	public GenericEntityDirectBlockDataMetadataTransformer(IdMappingTable blockTable, NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBlockData> index) {
		this.blockMappingTable = blockTable;
		this.index = index;
	}

	@Override
	public void accept(ArrayMap<NetworkEntityMetadataObject<?>> t) {
		NetworkEntityMetadataObjectBlockData blockdataObject = index.getObject(t);
		if (blockdataObject != null) {
			blockdataObject.setValue(blockMappingTable.get(blockdataObject.getValue()));
		}
	}

}