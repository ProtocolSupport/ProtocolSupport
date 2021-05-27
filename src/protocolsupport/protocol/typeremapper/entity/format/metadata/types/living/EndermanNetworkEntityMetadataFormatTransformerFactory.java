package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer.NetworkEntityMetadataList;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.block.PreFlatteningBlockIdData;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.NetworkEntityMetadataFormatTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueBooleanToByteTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.InsentientNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectBlockData;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectShort;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class EndermanNetworkEntityMetadataFormatTransformerFactory extends InsentientNetworkEntityMetadataFormatTransformerFactory {

	public static final EndermanNetworkEntityMetadataFormatTransformerFactory INSTANCE = new EndermanNetworkEntityMetadataFormatTransformerFactory();

	protected EndermanNetworkEntityMetadataFormatTransformerFactory() {
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Enderman.CARRIED_BLOCK, 15), ProtocolVersionsHelper.UP_1_15);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Enderman.CARRIED_BLOCK, 14), ProtocolVersionsHelper.ALL_1_14);
		add(version -> new NetworkEntityMetadataObjectIndexValueTransformer<NetworkEntityMetadataObjectBlockData>(NetworkEntityMetadataObjectIndex.Enderman.CARRIED_BLOCK, 12) {
			final FlatteningBlockDataTable flatteningBlockDataTable = FlatteningBlockData.REGISTRY.getTable(version);
			@Override
			public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectBlockData object) {
				return new NetworkEntityMetadataObjectBlockData(flatteningBlockDataTable.getBlockDataRemap(object.getValue()));
			}
		}, ProtocolVersionsHelper.ALL_1_13);
		add(version -> new NetworkEntityMetadataObjectIndexValueTransformer<NetworkEntityMetadataObjectBlockData>(NetworkEntityMetadataObjectIndex.Enderman.CARRIED_BLOCK, 12) {
			@Override
			public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectBlockData object) {
				return new NetworkEntityMetadataObjectBlockData(PreFlatteningBlockIdData.getCombinedIdM12(object.getValue()));
			}
		}, ProtocolVersionsHelper.RANGE__1_10__1_12_2);
		add(version -> new NetworkEntityMetadataObjectIndexValueTransformer<NetworkEntityMetadataObjectBlockData>(NetworkEntityMetadataObjectIndex.Enderman.CARRIED_BLOCK, 11) {
			@Override
			public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectBlockData object) {
				return new NetworkEntityMetadataObjectBlockData(PreFlatteningBlockIdData.getCombinedIdM12(object.getValue()));
			}
		}, ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueTransformer<NetworkEntityMetadataObjectBlockData>(NetworkEntityMetadataObjectIndex.Enderman.CARRIED_BLOCK, 16) {
			@Override
			public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectBlockData object) {
				return new NetworkEntityMetadataObjectShort((short) PreFlatteningBlockIdData.getCombinedIdM12(object.getValue()));
			}
		}, ProtocolVersion.MINECRAFT_1_8);
		add(version -> new NetworkEntityMetadataFormatTransformer() {
			@Override
			public void transform(NetworkEntity entity, ArrayMap<NetworkEntityMetadataObject<?>> original, NetworkEntityMetadataList remapped) {
				NetworkEntityMetadataObjectBlockData carriedBlockObject = NetworkEntityMetadataObjectIndex.Enderman.CARRIED_BLOCK.getObject(original);
				if (carriedBlockObject != null) {
					int legacyCombinedId = PreFlatteningBlockIdData.getCombinedId(carriedBlockObject.getValue());
					remapped.add(16, new NetworkEntityMetadataObjectByte((byte) PreFlatteningBlockIdData.getIdFromCombinedId(legacyCombinedId)));
					remapped.add(17, new NetworkEntityMetadataObjectByte((byte) PreFlatteningBlockIdData.getDataFromCombinedId(legacyCombinedId)));
				}
			}
		}, ProtocolVersionsHelper.DOWN_1_7_10);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Enderman.SCREAMING, 16), ProtocolVersionsHelper.UP_1_15);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Enderman.SCREAMING, 15), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Enderman.SCREAMING, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Enderman.SCREAMING, 12), ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueBooleanToByteTransformer(NetworkEntityMetadataObjectIndex.Enderman.SCREAMING, 18), ProtocolVersionsHelper.DOWN_1_8);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Enderman.STARED_AT, 17), ProtocolVersionsHelper.UP_1_15);
	}

}
