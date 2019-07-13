package protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable;

import protocolsupport.api.unsafe.pemetadata.PEMetaProviderSPI;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata.PeMetaBase;
import protocolsupport.protocol.typeremapper.entity.metadata.NetworkEntityMetadataObjectRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.AgeableEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNumberToByte;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNumberToSVarInt;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectFloatLe;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.utils.CollectionsUtils;

public class RabbitEntityMetadataRemapper extends AgeableEntityMetadataRemapper {

	public RabbitEntityMetadataRemapper() {
		addRemap(new NetworkEntityMetadataObjectRemapper() {
			@Override
			public void remap(NetworkEntity entity, CollectionsUtils.ArrayMap<NetworkEntityMetadataObject<?>> original, CollectionsUtils.ArrayMap<NetworkEntityMetadataObject<?>> remapped) {
				NetworkEntityMetadataObjectIndex.Rabbit.IS_BABY.getValue(original).ifPresent(boolWatcher -> {
					float entitySize = boolWatcher.getValue() ? 0.3f : 0.55f;

					entity.getDataCache().setPeBaseFlag(PeMetaBase.FLAG_BABY, boolWatcher.getValue());
					entity.getDataCache().setSizeModifier(entitySize);

					entitySize *= PEMetaProviderSPI.getProvider().getSizeScale(entity.getUUID(), entity.getId(), entity.getType().getBukkitType());

					PEDataValues.PEEntityData pocketdata = PEDataValues.getEntityData(entity.getType());

					remapped.put(PeMetaBase.SCALE, new NetworkEntityMetadataObjectFloatLe(entitySize));
					remapped.put(PeMetaBase.BOUNDINGBOX_WIDTH, new NetworkEntityMetadataObjectFloatLe(pocketdata.getBoundingBox().getWidth() * entitySize));
					remapped.put(PeMetaBase.BOUNDINGBOX_HEIGTH, new NetworkEntityMetadataObjectFloatLe(pocketdata.getBoundingBox().getHeight() * entitySize));
				});
			}
		}, ProtocolVersionsHelper.ALL_PE);

		addRemap(new IndexValueRemapperNumberToSVarInt(NetworkEntityMetadataObjectIndex.Rabbit.VARIANT, PeMetaBase.VARIANT), ProtocolVersionsHelper.ALL_PE);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Rabbit.VARIANT, 15), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Rabbit.VARIANT, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Rabbit.VARIANT, 12), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNumberToByte(NetworkEntityMetadataObjectIndex.Rabbit.VARIANT, 18), ProtocolVersionsHelper.BEFORE_1_9);
	}

}
