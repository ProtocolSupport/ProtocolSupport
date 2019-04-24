package protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable;

import protocolsupport.protocol.typeremapper.entity.metadata.types.base.AgeableEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperVillagerDataToVarInt;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectInt;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVillagerData;

public class VillagerEntityMetadataRemapper extends AgeableEntityMetadataRemapper {

	public VillagerEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Villager.VDATA, 15), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperVillagerDataToVarInt(DataWatcherObjectIndex.Villager.VDATA, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperVillagerDataToVarInt(DataWatcherObjectIndex.Villager.VDATA, 12), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapper<DataWatcherObjectVillagerData>(DataWatcherObjectIndex.Villager.VDATA, 16) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectVillagerData object) {
				return new DataWatcherObjectInt(object.getValue().getProfession());
			}
		}, ProtocolVersionsHelper.BEFORE_1_9);
	}

}
