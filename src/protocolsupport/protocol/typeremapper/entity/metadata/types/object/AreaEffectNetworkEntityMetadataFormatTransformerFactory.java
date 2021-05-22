package protocolsupport.protocol.typeremapper.entity.metadata.types.object;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer.NetworkEntityMetadataList;
import protocolsupport.protocol.typeremapper.entity.metadata.object.NetworkEntityMetadataFormatTransformer;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.BaseNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.legacy.LegacyParticle;
import protocolsupport.protocol.typeremapper.particle.LegacyParticleData;
import protocolsupport.protocol.typeremapper.particle.LegacyParticleData.LegacyParticleDataTable;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectParticle;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.protocol.types.particle.Particle;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class AreaEffectNetworkEntityMetadataFormatTransformerFactory extends BaseNetworkEntityMetadataFormatTransformerFactory {

	public static final AreaEffectNetworkEntityMetadataFormatTransformerFactory INSTANCE = new AreaEffectNetworkEntityMetadataFormatTransformerFactory();

	protected AreaEffectNetworkEntityMetadataFormatTransformerFactory() {
		addTransformer(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.AreaEffectCloud.RADIUS, 7), ProtocolVersionsHelper.UP_1_14);
		addTransformer(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.AreaEffectCloud.RADIUS, 6), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addTransformer(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.AreaEffectCloud.RADIUS, 5), ProtocolVersionsHelper.ALL_1_9);

		addTransformer(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.AreaEffectCloud.COLOR, 8), ProtocolVersionsHelper.UP_1_14);
		addTransformer(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.AreaEffectCloud.COLOR, 7), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addTransformer(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.AreaEffectCloud.COLOR, 6), ProtocolVersionsHelper.ALL_1_9);

		addTransformer(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.AreaEffectCloud.SINGLE_POINT, 9), ProtocolVersionsHelper.UP_1_14);
		addTransformer(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.AreaEffectCloud.SINGLE_POINT, 8), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addTransformer(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.AreaEffectCloud.SINGLE_POINT, 7), ProtocolVersionsHelper.ALL_1_9);

		addTransformerPerVersion(version -> new AreaEffectCloudModernParticleObjectMetadataTransformer(10, LegacyParticleData.REGISTRY.getTable(version)), ProtocolVersionsHelper.UP_1_14);
		addTransformerPerVersion(version -> new AreaEffectCloudModernParticleObjectMetadataTransformer(9, LegacyParticleData.REGISTRY.getTable(version)), ProtocolVersionsHelper.ALL_1_13);
		addTransformerPerVersion(version -> new AreaEffectCloudLegacyParticleObjectMetadataTransformer(9, version, LegacyParticleData.REGISTRY.getTable(version)), ProtocolVersionsHelper.RANGE__1_10__1_12_2);
		addTransformerPerVersion(version -> new AreaEffectCloudLegacyParticleObjectMetadataTransformer(8, version, LegacyParticleData.REGISTRY.getTable(version)), ProtocolVersionsHelper.ALL_1_9);
	}


	protected static class AreaEffectCloudModernParticleObjectMetadataTransformer extends NetworkEntityMetadataFormatTransformer {

		protected final LegacyParticleDataTable remapper;
		protected final int toIndex;

		public AreaEffectCloudModernParticleObjectMetadataTransformer(int toIndex, LegacyParticleDataTable remapper) {
			this.toIndex = toIndex;
			this.remapper = remapper;
		}

		@Override
		public void transform(NetworkEntity entity, ArrayMap<NetworkEntityMetadataObject<?>> original, NetworkEntityMetadataList remapped) {
			NetworkEntityMetadataObjectParticle particleObject = NetworkEntityMetadataObjectIndex.AreaEffectCloud.PARTICLE.getObject(original);
			if (particleObject != null) {
				Particle particle = remapper.get(particleObject.getValue().getClass()).apply(particleObject.getValue());
				if (particle != null) {
					remapped.add(toIndex, new NetworkEntityMetadataObjectParticle(particle));
				}
			}
		}

	}

	protected static class AreaEffectCloudLegacyParticleObjectMetadataTransformer extends NetworkEntityMetadataFormatTransformer {

		protected final int toIndex;
		protected final ProtocolVersion version;
		protected final LegacyParticleDataTable table;

		public AreaEffectCloudLegacyParticleObjectMetadataTransformer(int toIndex, ProtocolVersion version, LegacyParticleDataTable table) {
			this.toIndex = toIndex;
			this.version = version;
			this.table = table;
		}

		@Override
		public void transform(NetworkEntity entity, ArrayMap<NetworkEntityMetadataObject<?>> original, NetworkEntityMetadataList remapped) {
			NetworkEntityMetadataObjectParticle particleObject = NetworkEntityMetadataObjectIndex.AreaEffectCloud.PARTICLE.getObject(original);
			if (particleObject != null) {
				Particle particle = table.get(particleObject.getValue().getClass()).apply(particleObject.getValue());
				if (particle == null) {
					return;
				}
				remapped.add(toIndex, new NetworkEntityMetadataObjectVarInt(LegacyParticle.IntId.getId(particle)));
				int[] data = LegacyParticle.IntId.getData(version, particle);
				if (data.length >= 1) {
					remapped.add(toIndex + 1, new NetworkEntityMetadataObjectVarInt(data[0]));
				}
				if (data.length >= 2) {
					remapped.add(toIndex + 1, new NetworkEntityMetadataObjectVarInt(data[1]));
				}
			}
		}

	}

}
