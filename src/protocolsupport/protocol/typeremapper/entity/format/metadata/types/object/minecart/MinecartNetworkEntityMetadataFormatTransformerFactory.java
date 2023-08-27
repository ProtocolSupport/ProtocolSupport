package protocolsupport.protocol.typeremapper.entity.format.metadata.types.object.minecart;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.block.PreFlatteningBlockIdData;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueBooleanToByteTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNumberToIntTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.BaseNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectInt;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class MinecartNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.MinecartIndexRegistry> extends BaseNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final MinecartNetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry.MinecartIndexRegistry> INSTANCE = new MinecartNetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.MinecartIndexRegistry.INSTANCE);

	protected MinecartNetworkEntityMetadataFormatTransformerFactory(R registry) {
		super(registry);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SHAKING_POWER, 8), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SHAKING_POWER, 7), ProtocolVersionsHelper.RANGE__1_14__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SHAKING_POWER, 6), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SHAKING_POWER, 5), ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueNumberToIntTransformer(registry.SHAKING_POWER, 17), ProtocolVersionsHelper.DOWN_1_8);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SHAKING_DIRECTION, 9), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SHAKING_DIRECTION, 8), ProtocolVersionsHelper.RANGE__1_14__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SHAKING_DIRECTION, 7), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SHAKING_DIRECTION, 6), ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueNumberToIntTransformer(registry.SHAKING_DIRECTION, 18), ProtocolVersionsHelper.DOWN_1_8);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.DAMAGE_TAKEN, 10), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.DAMAGE_TAKEN, 9), ProtocolVersionsHelper.RANGE__1_14__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.DAMAGE_TAKEN, 8), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.DAMAGE_TAKEN, 7), ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.DAMAGE_TAKEN, 19), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1));
		add(new NetworkEntityMetadataObjectIndexValueNumberToIntTransformer(registry.DAMAGE_TAKEN, 19), ProtocolVersionsHelper.DOWN_1_5_2);

		add(
			version -> new MinecartNetworkEntityMetadataObjectBlockFlatteningIdTransformer(registry.BLOCK, 11, FlatteningBlockDataRegistry.INSTANCE.getTable(version)),
			ProtocolVersionsHelper.UP_1_17
		);
		add(
			version -> new MinecartNetworkEntityMetadataObjectBlockFlatteningIdTransformer(registry.BLOCK, 10, FlatteningBlockDataRegistry.INSTANCE.getTable(version)),
			ProtocolVersionsHelper.RANGE__1_14__1_16_4
		);
		add(
			version -> new MinecartNetworkEntityMetadataObjectBlockFlatteningIdTransformer(registry.BLOCK, 9, FlatteningBlockDataRegistry.INSTANCE.getTable(version)),
			ProtocolVersionsHelper.ALL_1_13
		);
		add(
			version -> new NetworkEntityMetadataObjectIndexValueTransformer<>(registry.BLOCK, 9) {
				@Override
				public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectVarInt object) {
					return new NetworkEntityMetadataObjectVarInt(PreFlatteningBlockIdData.getCombinedIdM12(object.getValue()));
				}
			},
			ProtocolVersionsHelper.RANGE__1_10__1_12_2
		);
		add(
			version -> new NetworkEntityMetadataObjectIndexValueTransformer<>(registry.BLOCK, 8) {
				@Override
				public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectVarInt object) {
					return new NetworkEntityMetadataObjectVarInt(PreFlatteningBlockIdData.getCombinedIdM12(object.getValue()));
				}
			},
			ProtocolVersionsHelper.ALL_1_9
		);
		add(
			new NetworkEntityMetadataObjectIndexValueTransformer<>(registry.BLOCK, 20) {
				@Override
				public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectVarInt object) {
					return new NetworkEntityMetadataObjectInt(PreFlatteningBlockIdData.getCombinedIdM12(object.getValue()));
				}
			}, ProtocolVersion.MINECRAFT_1_8
		);
		add(
			version -> new NetworkEntityMetadataObjectIndexValueTransformer<>(registry.BLOCK, 20) {
				@Override
				public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectVarInt object) {
					return new NetworkEntityMetadataObjectInt(PreFlatteningBlockIdData.getCombinedIdM16(object.getValue()));
				}
			}, ProtocolVersionsHelper.DOWN_1_7_10
		);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.BLOCK_Y, 12), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.BLOCK_Y, 11), ProtocolVersionsHelper.RANGE__1_14__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.BLOCK_Y, 10), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.BLOCK_Y, 9), ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueNumberToIntTransformer(registry.BLOCK_Y, 21), ProtocolVersionsHelper.DOWN_1_8);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SHOW_BLOCK, 13), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SHOW_BLOCK, 12), ProtocolVersionsHelper.RANGE__1_14__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SHOW_BLOCK, 11), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SHOW_BLOCK, 10), ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueBooleanToByteTransformer(registry.SHOW_BLOCK, 22), ProtocolVersionsHelper.DOWN_1_8);
	}

	protected static class MinecartNetworkEntityMetadataObjectBlockFlatteningIdTransformer extends NetworkEntityMetadataObjectIndexValueTransformer<NetworkEntityMetadataObjectVarInt> {

		protected final FlatteningBlockDataTable flatteningBlockDataTable;

		protected MinecartNetworkEntityMetadataObjectBlockFlatteningIdTransformer(NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> fromIndex, int toIndex, FlatteningBlockDataTable flatteningBlockDataTable) {
			super(fromIndex, toIndex);
			this.flatteningBlockDataTable = flatteningBlockDataTable;
		}

		@Override
		public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectVarInt object) {
			return new NetworkEntityMetadataObjectVarInt(flatteningBlockDataTable.getId(object.getValue()));
		}

	}

}
