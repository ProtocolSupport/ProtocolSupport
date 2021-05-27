package protocolsupport.protocol.typeremapper.entity.legacy.metadata;

import java.util.function.Consumer;

import protocolsupport.protocol.typeremapper.utils.MappingTable.IdMappingTable;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class GenericEntityVarIntBlockDataMetadataTransformer implements Consumer<ArrayMap<NetworkEntityMetadataObject<?>>> {

	protected final IdMappingTable blockMappingTable;
	protected final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> index;

	public GenericEntityVarIntBlockDataMetadataTransformer(IdMappingTable blockTable, NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> index) {
		this.blockMappingTable = blockTable;
		this.index = index;
	}

	@Override
	public void accept(ArrayMap<NetworkEntityMetadataObject<?>> t) {
		NetworkEntityMetadataObjectVarInt blockdataObject = index.getObject(t);
		if (blockdataObject != null) {
			blockdataObject.setValue(blockMappingTable.get(blockdataObject.getValue()));
		}
	}

}