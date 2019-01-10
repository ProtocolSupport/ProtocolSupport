package protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable;

import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata.PeMetaBase;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.AgeableEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNumberToInt;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectSVarInt;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVarInt;

public class VillagerEntityMetadataRemapper extends AgeableEntityMetadataRemapper {

	public VillagerEntityMetadataRemapper() {
		addRemap(new IndexValueRemapper<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Villager.PROFESSION, PeMetaBase.VARIANT) {
			@Override
			public DataWatcherObjectSVarInt remapValue(DataWatcherObjectVarInt object) {
				return new DataWatcherObjectSVarInt(object.getValue() == 5 ? 0 : object.getValue()); //TODO: use regular remapper when nitwit is implemented.
			}
		}, ProtocolVersionsHelper.ALL_PE);

		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Villager.PROFESSION, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Villager.PROFESSION, 12), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Villager.PROFESSION, 16), ProtocolVersionsHelper.BEFORE_1_9);
	}

}
