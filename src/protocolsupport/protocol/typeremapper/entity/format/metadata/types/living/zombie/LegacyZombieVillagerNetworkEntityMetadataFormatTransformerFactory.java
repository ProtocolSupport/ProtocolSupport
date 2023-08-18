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
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVillagerData;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class LegacyZombieVillagerNetworkEntityMetadataFormatTransformerFactory extends ZombieVillagerNetworkEntityMetadataFormatTransformerFactory {

	public static final LegacyZombieVillagerNetworkEntityMetadataFormatTransformerFactory INSTANCE = new LegacyZombieVillagerNetworkEntityMetadataFormatTransformerFactory();

	protected LegacyZombieVillagerNetworkEntityMetadataFormatTransformerFactory() {
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.ZombieVillager.CONVERTING, 14), ProtocolVersion.MINECRAFT_1_10);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.ZombieVillager.CONVERTING, 13), ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueBooleanToByteTransformer(NetworkEntityMetadataObjectIndex.ZombieVillager.CONVERTING, 14), ProtocolVersionsHelper.DOWN_1_8);

		add(new NetworkEntityMetadataLegacyZombieVillagerTypeTransformer(13), ProtocolVersion.MINECRAFT_1_10);
		add(new NetworkEntityMetadataLegacyZombieVillagerTypeTransformer(12), ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectAddOnFirstUpdateTransformer(13, new NetworkEntityMetadataObjectByte((byte) 1)), ProtocolVersionsHelper.DOWN_1_8);
	}

	protected static class NetworkEntityMetadataLegacyZombieVillagerTypeTransformer extends NetworkEntityMetadataFormatTransformer {

		protected final int toIndex;

		public NetworkEntityMetadataLegacyZombieVillagerTypeTransformer(int toIndex) {
			this.toIndex =  toIndex;
		}

		@Override
		public void transform(NetworkEntity entity, ArrayMap<NetworkEntityMetadataObject<?>> original, NetworkEntityMetadataList remapped) {
			NetworkEntityMetadataObjectVillagerData villagerData = NetworkEntityMetadataObjectIndex.ZombieVillager.VDATA.getObject(original);
			if (villagerData != null) {
				remapped.add(toIndex, new NetworkEntityMetadataObjectVarInt(LegacyVillagerProfession.toLegacyId(villagerData.getValue().getProfession()) + 1));
			} else if (entity.getDataCache().isFirstMeta()) {
				remapped.add(toIndex, new NetworkEntityMetadataObjectVarInt(1));
			}
		}

	}

}
