package protocolsupport.protocol.typeremapper.entity.metadata.types.living.horse;

import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata.PeMetaBase;
import protocolsupport.protocol.typeremapper.entity.metadata.DataWatcherObjectRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectSVarInt;
import protocolsupport.protocol.utils.networkentity.NetworkEntity;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class CargoHorseEntityMetadataRemapper extends BaseHorseEntityMetadataRemapper {

	public static final CargoHorseEntityMetadataRemapper INSTANCE = new CargoHorseEntityMetadataRemapper();

	public CargoHorseEntityMetadataRemapper() {
		addRemap(new DataWatcherObjectRemapper() {
			@Override
			public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
				DataWatcherObjectIndex.CargoHorse.HAS_CHEST.getValue(original)
				.ifPresent(boolWatcher -> {
					entity.getDataCache().setPeBaseFlag(PeMetaBase.FLAG_CHESTED, boolWatcher.getValue());
					remapped.put(PeMetaBase.HORSE_CONTAINER_MULTIPLIER, new DataWatcherObjectSVarInt(boolWatcher.getValue() ? 3 : 0)); //Strength multiplier for chest size.
				});
			}
		}, ProtocolVersionsHelper.ALL_PE);

		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.CargoHorse.HAS_CHEST, 15), ProtocolVersionsHelper.RANGE__1_11__1_13_2);
	}

}
