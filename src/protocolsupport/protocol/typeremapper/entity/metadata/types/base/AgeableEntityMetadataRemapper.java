package protocolsupport.protocol.typeremapper.entity.metadata.types.base;

import protocolsupport.api.unsafe.pemetadata.PEMetaProviderSPI;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata.PeMetaBase;
import protocolsupport.protocol.typeremapper.entity.metadata.NetworkEntityMetadataObjectRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNumberToInt;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectBoolean;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectInt;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectFloatLe;
import protocolsupport.utils.CollectionsUtils;

public class AgeableEntityMetadataRemapper extends InsentientEntityMetadataRemapper {

	public static final AgeableEntityMetadataRemapper INSTANCE = new AgeableEntityMetadataRemapper();

	public AgeableEntityMetadataRemapper() {
		addRemap(new NetworkEntityMetadataObjectRemapper() {
			@Override
			public void remap(NetworkEntity entity, CollectionsUtils.ArrayMap<NetworkEntityMetadataObject<?>> original, CollectionsUtils.ArrayMap<NetworkEntityMetadataObject<?>> remapped) {
				NetworkEntityMetadataObjectIndex.Ageable.IS_BABY.getValue(original).ifPresent(boolWatcher -> {
					entity.getDataCache().setPeBaseFlag(PeMetaBase.FLAG_BABY, boolWatcher.getValue());
					entity.getDataCache().setSizeModifier(boolWatcher.getValue() ? 0.5f : 1f);
				});
				//Send scale -> avoid big mobs with floating heads.
				remapped.put(PeMetaBase.SCALE, new NetworkEntityMetadataObjectFloatLe(entity.getDataCache().getSizeModifier() * PEMetaProviderSPI.getProvider().getSizeScale(
					entity.getUUID(), entity.getId(), entity.getType().getBukkitType()
				)));
			}
		}, ProtocolVersionsHelper.ALL_PE);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Ageable.IS_BABY, 14), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Ageable.IS_BABY, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Ageable.IS_BABY, 11), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapper<NetworkEntityMetadataObjectBoolean>(NetworkEntityMetadataObjectIndex.Ageable.IS_BABY, 12) {
			@Override
			public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObjectBoolean object) {
				return new NetworkEntityMetadataObjectInt((object.getValue() ? -1 : 0));
			}
		}, ProtocolVersionsHelper.BEFORE_1_8);

		addRemap(new IndexValueRemapperNumberToInt(NetworkEntityMetadataObjectIndex.Ageable.AGE_HACK, 12), ProtocolVersionsHelper.RANGE__1_6__1_7);
	}

}
