package protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.AbstractMerchantEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperVillagerDataToVarInt;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectInt;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVillagerData;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class VillagerEntityMetadataRemapper extends AbstractMerchantEntityMetadataRemapper {

	public VillagerEntityMetadataRemapper() {
		//TODO: villager remap for pe
		/*addRemap(new IndexValueRemapper<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Villager.PROFESSION, PeMetaBase.VARIANT) {
			@Override
			public DataWatcherObjectSVarInt remapValue(DataWatcherObjectVarInt object) {
				return new DataWatcherObjectSVarInt(object.getValue() == 5 ? 0 : object.getValue()); //TODO: use regular remapper when nitwit is implemented.
			}
		}, ProtocolVersionsHelper.ALL_PE);*/

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Villager.VDATA, 16), ProtocolVersionsHelper.UP_1_14_1);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Villager.VDATA, 15), ProtocolVersion.MINECRAFT_1_14);
		addRemap(new IndexValueRemapperVillagerDataToVarInt(NetworkEntityMetadataObjectIndex.Villager.VDATA, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperVillagerDataToVarInt(NetworkEntityMetadataObjectIndex.Villager.VDATA, 12), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapper<NetworkEntityMetadataObjectVillagerData>(NetworkEntityMetadataObjectIndex.Villager.VDATA, 16) {
			@Override
			public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObjectVillagerData object) {
				return new NetworkEntityMetadataObjectInt(object.getValue().getProfession());
			}
		}, ProtocolVersionsHelper.BEFORE_1_9);
	}

}
