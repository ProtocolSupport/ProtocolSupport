package protocolsupport.protocol.typeremapper.entity.metadata.types.living.horse;

import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata.PeMetaBase;
import protocolsupport.protocol.typeremapper.entity.metadata.NetworkEntityMetadataObjectRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNumberToSVarInt;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.types.networkentity.NetworkEntityLamaDataCache;
import protocolsupport.utils.CollectionsUtils.ArrayMap;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;

public class LamaEntityMetadataRemapper extends CargoHorseEntityMetadataRemapper {

	public static final LamaEntityMetadataRemapper INSTANCE = new LamaEntityMetadataRemapper();

	public LamaEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNumberToSVarInt(NetworkEntityMetadataObjectIndex.Lama.VARIANT, PeMetaBase.VARIANT), ProtocolVersionsHelper.ALL_PE);
		//addRemap(new IndexValueRemapperNumberToByte(DataWatcherObjectIndex.Lama.CARPET_COLOR, 3), ProtocolVersionsHelper.ALL_PE); TODO: Carpet Color. Done via slots instead?
		addRemap(new IndexValueRemapperNumberToSVarInt(NetworkEntityMetadataObjectIndex.Lama.STRENGTH, PeMetaBase.STRENGTH), ProtocolVersionsHelper.ALL_PE); //TODO: Should max strength also be added?
		addRemap(new NetworkEntityMetadataObjectRemapper() {
			@Override
			public void remap(NetworkEntity entity, ArrayMap<NetworkEntityMetadataObject<?>> original, ArrayMap<NetworkEntityMetadataObject<?>> remapped) {
				NetworkEntityMetadataObjectIndex.Lama.STRENGTH.getValue(original).ifPresent(intWatcher -> {
					if (entity.getDataCache() instanceof NetworkEntityLamaDataCache) {
						((NetworkEntityLamaDataCache) entity.getDataCache()).setStrength(intWatcher.getValue());
					}
				});
		}}, ProtocolVersionsHelper.ALL_PE);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Lama.STRENGTH, 18), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Lama.STRENGTH, 16), ProtocolVersionsHelper.RANGE__1_11__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Lama.CARPET_COLOR, 17), ProtocolVersionsHelper.RANGE__1_11__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Lama.VARIANT, 18), ProtocolVersionsHelper.RANGE__1_11__1_13_2);
	}

}
