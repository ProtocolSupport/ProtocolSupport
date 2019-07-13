package protocolsupport.protocol.typeremapper.entity.metadata.types.living.horse;

import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata.PeMetaBase;
import protocolsupport.protocol.typeremapper.entity.metadata.NetworkEntityMetadataObjectRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectSVarInt;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class CargoHorseEntityMetadataRemapper extends BaseHorseEntityMetadataRemapper {

	public static final CargoHorseEntityMetadataRemapper INSTANCE = new CargoHorseEntityMetadataRemapper();

	public CargoHorseEntityMetadataRemapper() {

		addRemap(new NetworkEntityMetadataObjectRemapper() {
			@Override
			public void remap(NetworkEntity entity, ArrayMap<NetworkEntityMetadataObject<?>> original, ArrayMap<NetworkEntityMetadataObject<?>> remapped) {
				NetworkEntityMetadataObjectIndex.CargoHorse.HAS_CHEST.getValue(original)
				.ifPresent(boolWatcher -> {
					entity.getDataCache().setPeBaseFlag(PeMetaBase.FLAG_CHESTED, boolWatcher.getValue());
					remapped.put(PeMetaBase.HORSE_CONTAINER_MULTIPLIER, new NetworkEntityMetadataObjectSVarInt(boolWatcher.getValue() ? 3 : 0)); //Strength multiplier for chest size.
				});
			}
		}, ProtocolVersionsHelper.ALL_PE);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.CargoHorse.HAS_CHEST, 17), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.CargoHorse.HAS_CHEST, 15), ProtocolVersionsHelper.RANGE__1_11__1_13_2);
	}

}
