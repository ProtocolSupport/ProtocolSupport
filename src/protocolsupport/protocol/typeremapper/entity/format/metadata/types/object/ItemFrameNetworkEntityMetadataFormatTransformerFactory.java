package protocolsupport.protocol.typeremapper.entity.format.metadata.types.object;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNumberToByteTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.BaseNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class ItemFrameNetworkEntityMetadataFormatTransformerFactory extends BaseNetworkEntityMetadataFormatTransformerFactory {

	public static final ItemFrameNetworkEntityMetadataFormatTransformerFactory INSTANCE = new ItemFrameNetworkEntityMetadataFormatTransformerFactory();

	protected ItemFrameNetworkEntityMetadataFormatTransformerFactory() {
		add(version -> new NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer(NetworkEntityMetadataObjectIndex.ItemFrame.ITEM, 7, version), ProtocolVersionsHelper.UP_1_14);
		add(version -> new NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer(NetworkEntityMetadataObjectIndex.ItemFrame.ITEM, 6, version), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(version -> new NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer(NetworkEntityMetadataObjectIndex.ItemFrame.ITEM, 5, version), ProtocolVersionsHelper.ALL_1_9);
		add(version -> new NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer(NetworkEntityMetadataObjectIndex.ItemFrame.ITEM, 8, version), ProtocolVersion.MINECRAFT_1_8);
		add(version -> new NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer(NetworkEntityMetadataObjectIndex.ItemFrame.ITEM, 2, version), ProtocolVersionsHelper.DOWN_1_7_10);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.ItemFrame.ROTATION, 8), ProtocolVersionsHelper.UP_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.ItemFrame.ROTATION, 7), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.ItemFrame.ROTATION, 6), ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueNumberToByteTransformer(NetworkEntityMetadataObjectIndex.ItemFrame.ROTATION, 9), ProtocolVersion.MINECRAFT_1_8);
		add(new NetworkEntityMetadataObjectIndexValueTransformer<NetworkEntityMetadataObjectVarInt>(NetworkEntityMetadataObjectIndex.ItemFrame.ROTATION, 3) {
			@Override
			public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectVarInt object) {
				return new NetworkEntityMetadataObjectByte((byte) (object.getValue() >> 1));
			}
		}, ProtocolVersionsHelper.DOWN_1_7_10);
	}

}
