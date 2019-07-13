package protocolsupport.protocol.typeremapper.entity.metadata.types.object;

import protocolsupport.protocol.typeremapper.entity.metadata.NetworkEntityMetadataObjectRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.BaseEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.legacy.LegacyParticle;
import protocolsupport.protocol.typeremapper.particle.ParticleRemapper;
import protocolsupport.protocol.typeremapper.particle.ParticleRemapper.ParticleRemappingTable;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.protocol.types.particle.Particle;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class AreaEffectCloudEntityMetadataRemapper extends BaseEntityMetadataRemapper {

	public AreaEffectCloudEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.AreaEffectCloud.RADIUS, 7), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.AreaEffectCloud.RADIUS, 6), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.AreaEffectCloud.RADIUS, 5), ProtocolVersionsHelper.ALL_1_9);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.AreaEffectCloud.COLOR, 8), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.AreaEffectCloud.COLOR, 7), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.AreaEffectCloud.COLOR, 6), ProtocolVersionsHelper.ALL_1_9);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.AreaEffectCloud.SINGLE_POINT, 9), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.AreaEffectCloud.SINGLE_POINT, 8), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.AreaEffectCloud.SINGLE_POINT, 7), ProtocolVersionsHelper.ALL_1_9);

		//TODO: actually remap particle metadata for modern versions too
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.AreaEffectCloud.PARTICLE, 10), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.AreaEffectCloud.PARTICLE, 9), ProtocolVersionsHelper.ALL_1_13);
		addRemapPerVersion(
			version -> new NetworkEntityMetadataObjectRemapper() {
				ParticleRemappingTable particleRemapper = ParticleRemapper.REGISTRY.getTable(version);
				@Override
				public void remap(NetworkEntity entity, ArrayMap<NetworkEntityMetadataObject<?>> original, ArrayMap<NetworkEntityMetadataObject<?>> remapped) {
					NetworkEntityMetadataObjectIndex.AreaEffectCloud.PARTICLE.getValue(original).ifPresent(particleObject -> {
						Particle particle = particleRemapper.getRemap(particleObject.getValue().getClass()).apply(particleObject.getValue());
						if (particle == null) {
							return;
						}
						remapped.put(9, new NetworkEntityMetadataObjectVarInt(LegacyParticle.IntId.getId(particle)));
						int[] data = LegacyParticle.IntId.getData(particle);
						if (data.length >= 1) {
							remapped.put(10, new NetworkEntityMetadataObjectVarInt(data[0]));
						}
						if (data.length >= 2) {
							remapped.put(11, new NetworkEntityMetadataObjectVarInt(data[1]));
						}
					});
				}
			},
			ProtocolVersionsHelper.RANGE__1_9__1_12_2
		);
	}

}
