package protocolsupport.protocol.typeremapper.entity.metadata.types.living.zombie;

import protocolsupport.api.unsafe.pemetadata.PEMetaProviderSPI;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata.PeMetaBase;
import protocolsupport.protocol.typeremapper.entity.metadata.NetworkEntityMetadataObjectRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.InsentientEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperBooleanToByte;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.typeremapper.pe.PEDataValues.PEEntityData;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectFloatLe;
import protocolsupport.utils.CollectionsUtils;

public class ZombieEntityMetadataRemapper extends InsentientEntityMetadataRemapper {

	public static final ZombieEntityMetadataRemapper INSTANCE = new ZombieEntityMetadataRemapper();

	public ZombieEntityMetadataRemapper() {
		addRemap(new NetworkEntityMetadataObjectRemapper() {
			@Override
			public void remap(NetworkEntity entity, CollectionsUtils.ArrayMap<NetworkEntityMetadataObject<?>> original, CollectionsUtils.ArrayMap<NetworkEntityMetadataObject<?>> remapped) {
				NetworkEntityMetadataObjectIndex.Zombie.BABY.getValue(original).ifPresent(boolWatcher -> {
					entity.getDataCache().setPeBaseFlag(PeMetaBase.FLAG_BABY, boolWatcher.getValue());
					float sizescale = PEMetaProviderSPI.getProvider().getSizeScale(entity.getUUID(), entity.getId(), entity.getType().getBukkitType());
					float entitySize = boolWatcher.getValue() ? 0.5f * sizescale : sizescale;
					remapped.put(PeMetaBase.SCALE, new NetworkEntityMetadataObjectFloatLe(entitySize)); //Send scale -> avoid big mobs with floating heads.
					PEEntityData pocketdata = PEDataValues.getEntityData(entity.getType());
					if (pocketdata.getBoundingBox() != null) {
						remapped.put(PeMetaBase.BOUNDINGBOX_WIDTH, new NetworkEntityMetadataObjectFloatLe(pocketdata.getBoundingBox().getWidth() * entitySize));
						remapped.put(PeMetaBase.BOUNDINGBOX_HEIGTH, new NetworkEntityMetadataObjectFloatLe(pocketdata.getBoundingBox().getHeight() * entitySize));
					}
				});
			}
		}, ProtocolVersionsHelper.ALL_PE);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Zombie.BABY, 14), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Zombie.BABY, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Zombie.BABY, 11), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperBooleanToByte(NetworkEntityMetadataObjectIndex.Zombie.BABY, 12), ProtocolVersionsHelper.BEFORE_1_9);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Zombie.DROWNING, 16), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Zombie.DROWNING, 15), ProtocolVersionsHelper.ALL_1_13);
	}

}
