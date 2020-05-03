package protocolsupport.protocol.typeremapper.entity.metadata.types.object;

import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer.NetworkEntityMetadataList;
import protocolsupport.protocol.typeremapper.entity.metadata.object.NetworkEntityMetadataObjectRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.BaseEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.legacy.LegacyParticle;
import protocolsupport.protocol.typeremapper.particle.ParticleRemapper;
import protocolsupport.protocol.typeremapper.particle.ParticleRemapper.ParticleRemappingTable;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectParticle;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.protocol.types.particle.Particle;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class AreaEffectCloudEntityMetadataRemapper extends BaseEntityMetadataRemapper {

	public static final AreaEffectCloudEntityMetadataRemapper INSTANCE = new AreaEffectCloudEntityMetadataRemapper();

	protected AreaEffectCloudEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.AreaEffectCloud.RADIUS, 7), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.AreaEffectCloud.RADIUS, 6), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.AreaEffectCloud.RADIUS, 5), ProtocolVersionsHelper.ALL_1_9);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.AreaEffectCloud.COLOR, 8), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.AreaEffectCloud.COLOR, 7), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.AreaEffectCloud.COLOR, 6), ProtocolVersionsHelper.ALL_1_9);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.AreaEffectCloud.SINGLE_POINT, 9), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.AreaEffectCloud.SINGLE_POINT, 8), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.AreaEffectCloud.SINGLE_POINT, 7), ProtocolVersionsHelper.ALL_1_9);

		addRemapPerVersion(version -> new AreaEffectCloudModernParticleObjectMetadataRemapper(10, ParticleRemapper.REGISTRY.getTable(version)), ProtocolVersionsHelper.UP_1_14);
		addRemapPerVersion(version -> new AreaEffectCloudModernParticleObjectMetadataRemapper(9, ParticleRemapper.REGISTRY.getTable(version)), ProtocolVersionsHelper.ALL_1_13);
		addRemapPerVersion(version -> new AreaEffectCloudLegacyParticleObjectMetadataRemapper(9, ParticleRemapper.REGISTRY.getTable(version)), ProtocolVersionsHelper.RANGE__1_10__1_12_2);
		addRemapPerVersion(version -> new AreaEffectCloudLegacyParticleObjectMetadataRemapper(8, ParticleRemapper.REGISTRY.getTable(version)), ProtocolVersionsHelper.ALL_1_9);
	}


	protected static class AreaEffectCloudModernParticleObjectMetadataRemapper extends NetworkEntityMetadataObjectRemapper {

		protected final ParticleRemappingTable remapper;
		protected final int toIndex;
		public AreaEffectCloudModernParticleObjectMetadataRemapper(int toIndex, ParticleRemappingTable remapper) {
			this.toIndex = toIndex;
			this.remapper = remapper;
		}

		@Override
		public void remap(NetworkEntity entity, ArrayMap<NetworkEntityMetadataObject<?>> original, NetworkEntityMetadataList remapped) {
			NetworkEntityMetadataObjectIndex.AreaEffectCloud.PARTICLE.getValue(original).ifPresent(particleObject -> {
				Particle particle = remapper.getRemap(particleObject.getValue().getClass()).apply(particleObject.getValue());
				if (particle == null) {
					return;
				}
				remapped.add(toIndex, new NetworkEntityMetadataObjectParticle(particle));
			});
		}

	}

	protected static class AreaEffectCloudLegacyParticleObjectMetadataRemapper extends NetworkEntityMetadataObjectRemapper {

		protected final ParticleRemappingTable remapper;
		protected final int toIndex;
		public AreaEffectCloudLegacyParticleObjectMetadataRemapper(int toIndex, ParticleRemappingTable remapper) {
			this.toIndex = toIndex;
			this.remapper = remapper;
		}

		@Override
		public void remap(NetworkEntity entity, ArrayMap<NetworkEntityMetadataObject<?>> original, NetworkEntityMetadataList remapped) {
			NetworkEntityMetadataObjectIndex.AreaEffectCloud.PARTICLE.getValue(original).ifPresent(particleObject -> {
				Particle particle = remapper.getRemap(particleObject.getValue().getClass()).apply(particleObject.getValue());
				if (particle == null) {
					return;
				}
				remapped.add(toIndex, new NetworkEntityMetadataObjectVarInt(LegacyParticle.IntId.getId(particle)));
				int[] data = LegacyParticle.IntId.getData(particle);
				if (data.length >= 1) {
					remapped.add(toIndex + 1, new NetworkEntityMetadataObjectVarInt(data[0]));
				}
				if (data.length >= 2) {
					remapped.add(toIndex + 1, new NetworkEntityMetadataObjectVarInt(data[1]));
				}
			});
		}

	}

}
