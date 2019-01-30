package protocolsupport.protocol.typeremapper.entity.metadata.types.object;

import protocolsupport.protocol.typeremapper.entity.metadata.DataWatcherObjectRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.BaseEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.particle.ParticleRemapper;
import protocolsupport.protocol.typeremapper.particle.ParticleRemapper.ParticleRemappingTable;
import protocolsupport.protocol.typeremapper.particle.legacy.LegacyParticle;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVarInt;
import protocolsupport.protocol.utils.networkentity.NetworkEntity;
import protocolsupport.protocol.utils.types.particle.Particle;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class AreaEffectCloudEntityMetadataRemapper extends BaseEntityMetadataRemapper {

	public AreaEffectCloudEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.AreaEffectCloud.PARTICLE, 9), ProtocolVersionsHelper.UP_1_13);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.AreaEffectCloud.RADIUS, 6), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.AreaEffectCloud.RADIUS, 5), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.AreaEffectCloud.COLOR, 7), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.AreaEffectCloud.COLOR, 6), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.AreaEffectCloud.SINGLE_POINT, 8), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.AreaEffectCloud.SINGLE_POINT, 7), ProtocolVersionsHelper.ALL_1_9);
		addRemapPerVersion(
			version -> new DataWatcherObjectRemapper() {
				ParticleRemappingTable particleRemapper = ParticleRemapper.REGISTRY.getTable(version);
				@Override
				public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
					DataWatcherObjectIndex.AreaEffectCloud.PARTICLE.getValue(original).ifPresent(particleObject -> {
						Particle particle = particleRemapper.getRemap(particleObject.getValue().getClass()).apply(particleObject.getValue());
						remapped.put(9, new DataWatcherObjectVarInt(particle.getId()));
						if (particle instanceof LegacyParticle) {
							LegacyParticle lParticle = (LegacyParticle) particle;
							remapped.put(10, new DataWatcherObjectVarInt(lParticle.getFirstParameter()));
							remapped.put(11, new DataWatcherObjectVarInt(lParticle.getSecondParameter()));
						}
					});
				}
			},
			ProtocolVersionsHelper.RANGE__1_9__1_12_2
		);
	}

}
