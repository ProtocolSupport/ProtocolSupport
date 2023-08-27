package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.NetworkEntityMetadataCodec.NetworkEntityMetadataList;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.block.PreFlatteningBlockIdData;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.NetworkEntityMetadataFormatTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueBooleanToByteTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.InsentientNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectBlockData;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectShort;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class EndermanNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.EndermanIndexRegistry> extends InsentientNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final EndermanNetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry.EndermanIndexRegistry> INSTANCE = new EndermanNetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.EndermanIndexRegistry.INSTANCE);

	protected EndermanNetworkEntityMetadataFormatTransformerFactory(R registry) {
		super(registry);

		add(
			version -> new EndermanNetworkEntityMetadataObjectCarriedBlockFlatteningIdTransformer(registry.CARRIED_BLOCK, 16, FlatteningBlockDataRegistry.INSTANCE.getTable(version)),
			ProtocolVersionsHelper.UP_1_17
		);
		add(
			version -> new EndermanNetworkEntityMetadataObjectCarriedBlockFlatteningIdTransformer(registry.CARRIED_BLOCK, 15, FlatteningBlockDataRegistry.INSTANCE.getTable(version)),
			ProtocolVersionsHelper.RANGE__1_15__1_16_4
		);
		add(
			version -> new EndermanNetworkEntityMetadataObjectCarriedBlockFlatteningIdTransformer(registry.CARRIED_BLOCK, 14, FlatteningBlockDataRegistry.INSTANCE.getTable(version)),
			ProtocolVersionsHelper.ALL_1_14
		);
		add(
			version -> new EndermanNetworkEntityMetadataObjectCarriedBlockFlatteningIdTransformer(registry.CARRIED_BLOCK, 12, FlatteningBlockDataRegistry.INSTANCE.getTable(version)),
			ProtocolVersionsHelper.ALL_1_13
		);
		add(version -> new NetworkEntityMetadataObjectIndexValueTransformer<>(registry.CARRIED_BLOCK, 12) {
			@Override
			public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectBlockData object) {
				return new NetworkEntityMetadataObjectBlockData(PreFlatteningBlockIdData.getCombinedIdM12(object.getValue()));
			}
		}, ProtocolVersionsHelper.RANGE__1_10__1_12_2);
		add(version -> new NetworkEntityMetadataObjectIndexValueTransformer<>(registry.CARRIED_BLOCK, 11) {
			@Override
			public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectBlockData object) {
				return new NetworkEntityMetadataObjectBlockData(PreFlatteningBlockIdData.getCombinedIdM12(object.getValue()));
			}
		}, ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueTransformer<>(registry.CARRIED_BLOCK, 16) {
			@Override
			public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectBlockData object) {
				return new NetworkEntityMetadataObjectShort((short) PreFlatteningBlockIdData.getCombinedIdM12(object.getValue()));
			}
		}, ProtocolVersion.MINECRAFT_1_8);
		add(version -> new NetworkEntityMetadataFormatTransformer() {
			@Override
			public void transform(NetworkEntity entity, ArrayMap<NetworkEntityMetadataObject<?>> original, NetworkEntityMetadataList remapped) {
				NetworkEntityMetadataObjectBlockData carriedBlockObject = registry.CARRIED_BLOCK.getObject(original);
				if (carriedBlockObject != null) {
					int legacyCombinedId = PreFlatteningBlockIdData.getCombinedId(carriedBlockObject.getValue());
					remapped.add(16, new NetworkEntityMetadataObjectByte((byte) PreFlatteningBlockIdData.getIdFromCombinedId(legacyCombinedId)));
					remapped.add(17, new NetworkEntityMetadataObjectByte((byte) PreFlatteningBlockIdData.getDataFromCombinedId(legacyCombinedId)));
				}
			}
		}, ProtocolVersionsHelper.DOWN_1_7_10);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SCREAMING, 17), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SCREAMING, 16), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SCREAMING, 15), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SCREAMING, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SCREAMING, 12), ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueBooleanToByteTransformer(registry.SCREAMING, 18), ProtocolVersionsHelper.DOWN_1_8);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.STARED_AT, 18), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.STARED_AT, 17), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
	}

	protected static class EndermanNetworkEntityMetadataObjectCarriedBlockFlatteningIdTransformer extends NetworkEntityMetadataObjectIndexValueTransformer<NetworkEntityMetadataObjectBlockData> {

		protected final FlatteningBlockDataTable flatteningBlockDataTable;

		protected EndermanNetworkEntityMetadataObjectCarriedBlockFlatteningIdTransformer(NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBlockData> fromIndex, int toIndex, FlatteningBlockDataTable flatteningBlockDataTable) {
			super(fromIndex, toIndex);
			this.flatteningBlockDataTable = flatteningBlockDataTable;
		}

		@Override
		public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectBlockData object) {
			return new NetworkEntityMetadataObjectBlockData(flatteningBlockDataTable.getId(object.getValue()));
		}

	}

}
