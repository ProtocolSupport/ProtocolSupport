package protocolsupport.protocol.typeremapper.entity.format.metadata.types.object;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.NetworkEntityMetadataCodec.NetworkEntityMetadataList;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.NetworkEntityMetadataFormatTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.BaseNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.particle.PreFlatteningNetworkParticleIntIdRegistryDataSerializer;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectParticle;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.protocol.types.particle.NetworkParticle;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class AreaEffectNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.AreaEffectCloudIndexRegistry> extends BaseNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final AreaEffectNetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry.AreaEffectCloudIndexRegistry> INSTANCE = new AreaEffectNetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.AreaEffectCloudIndexRegistry.INSTANCE);

	protected AreaEffectNetworkEntityMetadataFormatTransformerFactory(R registry) {
		super(registry);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.RADIUS, 8), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.RADIUS, 7), ProtocolVersionsHelper.RANGE__1_14__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.RADIUS, 6), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.RADIUS, 5), ProtocolVersionsHelper.ALL_1_9);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.COLOR, 9), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.COLOR, 8), ProtocolVersionsHelper.RANGE__1_14__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.COLOR, 7), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.COLOR, 6), ProtocolVersionsHelper.ALL_1_9);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SINGLE_POINT, 10), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SINGLE_POINT, 9), ProtocolVersionsHelper.RANGE__1_14__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SINGLE_POINT, 8), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SINGLE_POINT, 7), ProtocolVersionsHelper.ALL_1_9);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.PARTICLE, 11), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.PARTICLE, 10), ProtocolVersionsHelper.RANGE__1_14__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.PARTICLE, 9), ProtocolVersionsHelper.ALL_1_13);
		add(version -> new AreaEffectCloudNetworkEntityMetadataObjectParticleLegacySplitTransformer(registry.PARTICLE, 9, version), ProtocolVersionsHelper.RANGE__1_10__1_12_2);
		add(version -> new AreaEffectCloudNetworkEntityMetadataObjectParticleLegacySplitTransformer(registry.PARTICLE, 8, version), ProtocolVersionsHelper.ALL_1_9);
	}


	protected static class AreaEffectCloudNetworkEntityMetadataObjectParticleLegacySplitTransformer extends NetworkEntityMetadataFormatTransformer {

		protected final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectParticle> fromIndex;
		protected final int toIndex;
		protected final ProtocolVersion version;

		public AreaEffectCloudNetworkEntityMetadataObjectParticleLegacySplitTransformer(NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectParticle> fromIndex, int toIndex, ProtocolVersion version) {
			this.fromIndex = fromIndex;
			this.toIndex = toIndex;
			this.version = version;
		}

		@Override
		public void transform(NetworkEntity entity, ArrayMap<NetworkEntityMetadataObject<?>> original, NetworkEntityMetadataList remapped) {
			NetworkEntityMetadataObjectParticle particleObject = fromIndex.getObject(original);
			if (particleObject != null) {
				NetworkParticle particle = particleObject.getValue();
				remapped.add(toIndex, new NetworkEntityMetadataObjectVarInt(PreFlatteningNetworkParticleIntIdRegistryDataSerializer.getId(particle)));
				int[] data = PreFlatteningNetworkParticleIntIdRegistryDataSerializer.getData(version, particle);
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
