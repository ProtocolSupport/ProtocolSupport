package protocolsupport.protocol.typeremapper.entity.format.metadata.types.object;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer.NetworkEntityMetadataList;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.NetworkEntityMetadataFormatTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.BaseNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.legacy.LegacyParticle;
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
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.AreaEffectCloud.RADIUS, 7), ProtocolVersionsHelper.UP_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.AreaEffectCloud.RADIUS, 6), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.AreaEffectCloud.RADIUS, 5), ProtocolVersionsHelper.ALL_1_9);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.AreaEffectCloud.COLOR, 8), ProtocolVersionsHelper.UP_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.AreaEffectCloud.COLOR, 7), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.AreaEffectCloud.COLOR, 6), ProtocolVersionsHelper.ALL_1_9);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.AreaEffectCloud.SINGLE_POINT, 9), ProtocolVersionsHelper.UP_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.AreaEffectCloud.SINGLE_POINT, 8), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.AreaEffectCloud.SINGLE_POINT, 7), ProtocolVersionsHelper.ALL_1_9);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.AreaEffectCloud.PARTICLE, 10), ProtocolVersionsHelper.UP_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.AreaEffectCloud.PARTICLE, 9), ProtocolVersionsHelper.ALL_1_13);
		add(version -> new AreaEffectCloudLegacyParticleObjectMetadataTransformer(9, version), ProtocolVersionsHelper.RANGE__1_10__1_12_2);
		add(version -> new AreaEffectCloudLegacyParticleObjectMetadataTransformer(8, version), ProtocolVersionsHelper.ALL_1_9);
	}


	protected static class AreaEffectCloudLegacyParticleObjectMetadataTransformer extends NetworkEntityMetadataFormatTransformer {

		protected final int toIndex;
		protected final ProtocolVersion version;

		public AreaEffectCloudLegacyParticleObjectMetadataTransformer(int toIndex, ProtocolVersion version) {
			this.toIndex = toIndex;
			this.version = version;
		}

		@Override
		public void transform(NetworkEntity entity, ArrayMap<NetworkEntityMetadataObject<?>> original, NetworkEntityMetadataList remapped) {
			NetworkEntityMetadataObjectParticle particleObject = NetworkEntityMetadataObjectIndex.AreaEffectCloud.PARTICLE.getObject(original);
			if (particleObject != null) {
				Particle particle = particleObject.getValue();
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
