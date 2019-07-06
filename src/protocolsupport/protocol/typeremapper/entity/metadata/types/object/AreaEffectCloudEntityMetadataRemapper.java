package protocolsupport.protocol.typeremapper.entity.metadata.types.object;

import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata.PeMetaBase;
import protocolsupport.protocol.typeremapper.entity.metadata.NetworkEntityMetadataObjectRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.BaseEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNumberToFloatLe;
import protocolsupport.protocol.typeremapper.particle.ParticleRemapper;
import protocolsupport.protocol.typeremapper.particle.ParticleRemapper.ParticleRemappingTable;
import protocolsupport.protocol.typeremapper.particle.legacy.LegacyParticle;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.protocol.types.particle.Particle;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class AreaEffectCloudEntityMetadataRemapper extends BaseEntityMetadataRemapper {

	public AreaEffectCloudEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNumberToFloatLe(NetworkEntityMetadataObjectIndex.AreaEffectCloud.RADIUS, PeMetaBase.AREA_EFFECT_RADIUS), ProtocolVersionsHelper.ALL_PE);
		//TODO: area effectcloud waiting? Particle?
		//addRemap(new IndexValueRemapperNumberToSVarInt(NetworkEntityMetadataObjectIndex.AreaEffectCloud.PARTICLE, PeMetaBase.AREA_EFFECT_PARTICLE), ProtocolVersionsHelper.ALL_PE),

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.AreaEffectCloud.RADIUS, 7), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.AreaEffectCloud.RADIUS, 6), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.AreaEffectCloud.RADIUS, 5), ProtocolVersionsHelper.ALL_1_9);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.AreaEffectCloud.COLOR, 8), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.AreaEffectCloud.COLOR, 7), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.AreaEffectCloud.COLOR, 6), ProtocolVersionsHelper.ALL_1_9);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.AreaEffectCloud.SINGLE_POINT, 9), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.AreaEffectCloud.SINGLE_POINT, 8), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.AreaEffectCloud.SINGLE_POINT, 7), ProtocolVersionsHelper.ALL_1_9);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.AreaEffectCloud.PARTICLE, 10), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.AreaEffectCloud.PARTICLE, 9), ProtocolVersionsHelper.ALL_1_13);
		addRemapPerVersion(
			version -> new NetworkEntityMetadataObjectRemapper() {
				ParticleRemappingTable particleRemapper = ParticleRemapper.REGISTRY.getTable(version);
				@Override
				public void remap(NetworkEntity entity, ArrayMap<NetworkEntityMetadataObject<?>> original, ArrayMap<NetworkEntityMetadataObject<?>> remapped) {
					NetworkEntityMetadataObjectIndex.AreaEffectCloud.PARTICLE.getValue(original).ifPresent(particleObject -> {
						Particle particle = particleRemapper.getRemap(particleObject.getValue().getClass()).apply(particleObject.getValue());
						remapped.put(9, new NetworkEntityMetadataObjectVarInt(particle.getId()));
						if (particle instanceof LegacyParticle) {
							LegacyParticle lParticle = (LegacyParticle) particle;
							remapped.put(10, new NetworkEntityMetadataObjectVarInt(lParticle.getFirstParameter()));
							remapped.put(11, new NetworkEntityMetadataObjectVarInt(lParticle.getSecondParameter()));
						}
					});
				}
			},
			ProtocolVersionsHelper.RANGE__1_9__1_12_2
		);
	}

}
