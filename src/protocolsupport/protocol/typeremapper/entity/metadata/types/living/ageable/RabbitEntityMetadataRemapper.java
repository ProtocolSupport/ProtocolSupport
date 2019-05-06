package protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable;

import protocolsupport.api.unsafe.pemetadata.PEMetaProviderSPI;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata.PeMetaBase;
import protocolsupport.protocol.typeremapper.entity.metadata.DataWatcherObjectRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.AgeableEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNumberToByte;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNumberToSVarInt;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectFloatLe;
import protocolsupport.protocol.utils.networkentity.NetworkEntity;
import protocolsupport.utils.CollectionsUtils;

public class RabbitEntityMetadataRemapper extends AgeableEntityMetadataRemapper {

	public RabbitEntityMetadataRemapper() {
		addRemap(new DataWatcherObjectRemapper() {
			@Override
			public void remap(NetworkEntity entity, CollectionsUtils.ArrayMap<DataWatcherObject<?>> original, CollectionsUtils.ArrayMap<DataWatcherObject<?>> remapped) {
				DataWatcherObjectIndex.Rabbit.IS_BABY.getValue(original).ifPresent(boolWatcher -> {
					float entitySize = boolWatcher.getValue() ? 0.3f : 0.55f;

					entity.getDataCache().setPeBaseFlag(PeMetaBase.FLAG_BABY, boolWatcher.getValue());
					entity.getDataCache().setSizeModifier(entitySize);

					entitySize *= PEMetaProviderSPI.getProvider().getSizeScale(entity.getUUID(), entity.getId(), entity.getType().getBukkitType());

					PEDataValues.PEEntityData pocketdata = PEDataValues.getEntityData(entity.getType());

					remapped.put(PeMetaBase.SCALE, new DataWatcherObjectFloatLe(entitySize));
					remapped.put(PeMetaBase.BOUNDINGBOX_WIDTH, new DataWatcherObjectFloatLe(pocketdata.getBoundingBox().getWidth() * entitySize));
					remapped.put(PeMetaBase.BOUNDINGBOX_HEIGTH, new DataWatcherObjectFloatLe(pocketdata.getBoundingBox().getHeight() * entitySize));
				});
			}
		}, ProtocolVersionsHelper.ALL_PE);

		addRemap(new IndexValueRemapperNumberToSVarInt(DataWatcherObjectIndex.Rabbit.VARIANT, PeMetaBase.VARIANT), ProtocolVersionsHelper.ALL_PE);

		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Rabbit.VARIANT, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Rabbit.VARIANT, 12), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNumberToByte(DataWatcherObjectIndex.Rabbit.VARIANT, 18), ProtocolVersionsHelper.BEFORE_1_9);
	}

}
