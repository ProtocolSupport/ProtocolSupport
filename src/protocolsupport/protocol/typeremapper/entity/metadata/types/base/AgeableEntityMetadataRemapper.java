package protocolsupport.protocol.typeremapper.entity.metadata.types.base;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.unsafe.pemetadata.PEMetaProviderSPI;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata.PeMetaBase;
import protocolsupport.protocol.typeremapper.entity.metadata.DataWatcherObjectRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNumberToInt;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectBoolean;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectByte;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectFloatLe;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectInt;
import protocolsupport.protocol.utils.networkentity.NetworkEntity;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class AgeableEntityMetadataRemapper extends InsentientEntityMetadataRemapper {

	public static final AgeableEntityMetadataRemapper INSTANCE = new AgeableEntityMetadataRemapper();

	public AgeableEntityMetadataRemapper() {
		addRemap(new DataWatcherObjectRemapper() {
			@Override
			public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
				DataWatcherObjectIndex.Ageable.IS_BABY.getValue(original).ifPresent(boolWatcher -> {
					entity.getDataCache().setPeBaseFlag(PeMetaBase.FLAG_BABY, boolWatcher.getValue());
					entity.getDataCache().setSizeModifier(boolWatcher.getValue() ? 0.5f : 1f);
				});
				//Send scale -> avoid big mobs with floating heads.
				remapped.put(PeMetaBase.SCALE, new DataWatcherObjectFloatLe(entity.getDataCache().getSizeModifier() * PEMetaProviderSPI.getProvider().getSizeScale(
					entity.getUUID(), entity.getId(), entity.getType().getBukkitType()
				)));
			}
		}, ProtocolVersion.MINECRAFT_PE);

		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Ageable.IS_BABY, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Ageable.IS_BABY, 11), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapper<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Ageable.IS_BABY, 12) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectBoolean object) {
				return new DataWatcherObjectByte((byte) (object.getValue() ? -1 : 0));
			}
		}, ProtocolVersion.MINECRAFT_1_8);
		addRemap(new IndexValueRemapper<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Ageable.IS_BABY, 12) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectBoolean object) {
				return new DataWatcherObjectInt((object.getValue() ? -1 : 0));
			}
		}, ProtocolVersionsHelper.BEFORE_1_8);

		addRemap(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Ageable.AGE_HACK, 12), ProtocolVersionsHelper.RANGE__1_6__1_7);
	}

}
