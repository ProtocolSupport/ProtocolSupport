package protocolsupport.protocol.typeremapper.entity.metadata.types.living.horse;

import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata.PeMetaBase;
import protocolsupport.protocol.typeremapper.entity.metadata.DataWatcherObjectRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNumberToSVarInt;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;
import protocolsupport.protocol.utils.networkentity.NetworkEntity;
import protocolsupport.protocol.utils.networkentity.NetworkEntityLamaDataCache;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class LamaEntityMetadataRemapper extends CargoHorseEntityMetadataRemapper {

	public static final LamaEntityMetadataRemapper INSTANCE = new LamaEntityMetadataRemapper();

	public LamaEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNumberToSVarInt(DataWatcherObjectIndex.Lama.VARIANT, PeMetaBase.VARIANT), ProtocolVersionsHelper.ALL_PE);
		//addRemap(new IndexValueRemapperNumberToByte(DataWatcherObjectIndex.Lama.CARPET_COLOR, 3), ProtocolVersionsHelper.ALL_PE); TODO: Carpet Color. Done via slots instead?
		addRemap(new IndexValueRemapperNumberToSVarInt(DataWatcherObjectIndex.Lama.STRENGTH, PeMetaBase.STRENGTH), ProtocolVersionsHelper.ALL_PE); //TODO: Should max strength also be added?
		addRemap(new DataWatcherObjectRemapper() {
			@Override
			public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
				DataWatcherObjectIndex.Lama.STRENGTH.getValue(original).ifPresent(intWatcher -> {
					((NetworkEntityLamaDataCache) entity.getDataCache()).setStrength(intWatcher.getValue());
				});
		}}, ProtocolVersionsHelper.ALL_PE);

		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Lama.STRENGTH, 16), ProtocolVersionsHelper.RANGE__1_11__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Lama.CARPET_COLOR, 17), ProtocolVersionsHelper.RANGE__1_11__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Lama.VARIANT, 18), ProtocolVersionsHelper.RANGE__1_11__1_13_2);
	}

}
