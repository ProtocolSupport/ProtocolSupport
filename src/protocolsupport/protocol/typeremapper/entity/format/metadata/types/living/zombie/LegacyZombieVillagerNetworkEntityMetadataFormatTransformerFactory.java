package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.zombie;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.NetworkEntityMetadataCodec.NetworkEntityMetadataList;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.NetworkEntityMetadataFormatTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.misc.NetworkEntityMetadataObjectAddOnFirstUpdateTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueBooleanToByteTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.legacy.LegacyVillagerProfession;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVillagerData;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class LegacyZombieVillagerNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.ZombieVillagerIndexRegistry> extends ZombieVillagerNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final LegacyZombieVillagerNetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry.ZombieVillagerIndexRegistry> INSTANCE = new LegacyZombieVillagerNetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.ZombieVillagerIndexRegistry.INSTANCE);

	protected LegacyZombieVillagerNetworkEntityMetadataFormatTransformerFactory(R registry) {
		super(registry);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.CONVERTING, 14), ProtocolVersion.MINECRAFT_1_10);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.CONVERTING, 13), ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueBooleanToByteTransformer(registry.CONVERTING, 14), ProtocolVersionsHelper.DOWN_1_8);

		add(new NetworkEntityMetadataLegacyZombieVillagerTypeTransformer(registry.VDATA, 13), ProtocolVersion.MINECRAFT_1_10);
		add(new NetworkEntityMetadataLegacyZombieVillagerTypeTransformer(registry.VDATA, 12), ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectAddOnFirstUpdateTransformer(13, new NetworkEntityMetadataObjectByte((byte) 1)), ProtocolVersionsHelper.DOWN_1_8);
	}

	protected static class NetworkEntityMetadataLegacyZombieVillagerTypeTransformer extends NetworkEntityMetadataFormatTransformer {

		protected final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVillagerData> fromIndex;
		protected final int toIndex;

		public NetworkEntityMetadataLegacyZombieVillagerTypeTransformer(NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVillagerData> fromIndex, int toIndex) {
			this.fromIndex = fromIndex;
			this.toIndex =  toIndex;
		}

		@Override
		public void transform(NetworkEntity entity, ArrayMap<NetworkEntityMetadataObject<?>> original, NetworkEntityMetadataList remapped) {
			NetworkEntityMetadataObjectVillagerData villagerData = fromIndex.getObject(original);
			if (villagerData != null) {
				remapped.add(toIndex, new NetworkEntityMetadataObjectVarInt(LegacyVillagerProfession.toLegacyId(villagerData.getValue().getProfession()) + 1));
			} else if (entity.getDataCache().isFirstMeta()) {
				remapped.add(toIndex, new NetworkEntityMetadataObjectVarInt(1));
			}
		}

	}

}
