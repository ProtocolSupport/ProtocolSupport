package protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable;

import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata.PeMetaBase;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.AgeableEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperVillagerDataToVarInt;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectSVarInt;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVarInt;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectInt;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVillagerData;

public class VillagerEntityMetadataRemapper extends AgeableEntityMetadataRemapper {

	public VillagerEntityMetadataRemapper() {
		//TODO: villager remap for pe
		/*addRemap(new IndexValueRemapper<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Villager.PROFESSION, PeMetaBase.VARIANT) {
			@Override
			public DataWatcherObjectSVarInt remapValue(DataWatcherObjectVarInt object) {
				return new DataWatcherObjectSVarInt(object.getValue() == 5 ? 0 : object.getValue()); //TODO: use regular remapper when nitwit is implemented.
			}
		}, ProtocolVersionsHelper.ALL_PE);*/

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
