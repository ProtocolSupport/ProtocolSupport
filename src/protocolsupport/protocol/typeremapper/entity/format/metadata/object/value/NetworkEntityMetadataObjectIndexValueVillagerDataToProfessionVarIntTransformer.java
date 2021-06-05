package protocolsupport.protocol.typeremapper.entity.format.metadata.object.value;

import protocolsupport.protocol.typeremapper.legacy.LegacyVillagerProfession;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVillagerData;

public class NetworkEntityMetadataObjectIndexValueVillagerDataToProfessionVarIntTransformer extends NetworkEntityMetadataObjectIndexValueTransformer<NetworkEntityMetadataObjectVillagerData> {

	public NetworkEntityMetadataObjectIndexValueVillagerDataToProfessionVarIntTransformer(NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVillagerData> fromIndex, int toIndex) {
		super(fromIndex, toIndex);
	}

	@Override
	public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectVillagerData object) {
		return new NetworkEntityMetadataObjectVarInt(LegacyVillagerProfession.toLegacyId(object.getValue().getProfession()));
	}

}
