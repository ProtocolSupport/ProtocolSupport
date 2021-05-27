package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.tameable;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueBooleanToByteTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNumberToByteTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.TameableNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class WolfNetworkEntityMetadataFormatTransformerFactory extends TameableNetworkEntityMetadataFormatTransformerFactory {

	public static final WolfNetworkEntityMetadataFormatTransformerFactory INSTANCE = new WolfNetworkEntityMetadataFormatTransformerFactory();

	protected WolfNetworkEntityMetadataFormatTransformerFactory() {
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Wolf.BEGGING, 18), ProtocolVersionsHelper.UP_1_15);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Wolf.BEGGING, 18), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Wolf.BEGGING, 16), ProtocolVersionsHelper.RANGE__1_11__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Wolf.BEGGING, 16), ProtocolVersion.MINECRAFT_1_10);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Wolf.BEGGING, 15), ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueBooleanToByteTransformer(NetworkEntityMetadataObjectIndex.Wolf.BEGGING, 19), ProtocolVersionsHelper.DOWN_1_8);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Wolf.COLLAR_COLOR, 19), ProtocolVersionsHelper.UP_1_15);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Wolf.COLLAR_COLOR, 19), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Wolf.COLLAR_COLOR, 17), ProtocolVersionsHelper.ALL_1_13);
		add(new VarIntWolfCollarColorIndexValueRemapper(17), ProtocolVersionsHelper.RANGE__1_10__1_12_2);
		add(new VarIntWolfCollarColorIndexValueRemapper(16), ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueTransformer<NetworkEntityMetadataObjectVarInt>(NetworkEntityMetadataObjectIndex.Wolf.COLLAR_COLOR, 20) {
			@Override
			public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectVarInt object) {
				return new NetworkEntityMetadataObjectByte((byte) (15 - object.getValue()));
			}
		}, ProtocolVersion.MINECRAFT_1_8);
		add(new NetworkEntityMetadataObjectIndexValueNumberToByteTransformer(NetworkEntityMetadataObjectIndex.Wolf.COLLAR_COLOR, 20), ProtocolVersionsHelper.DOWN_1_7_10);
	}

	protected static class VarIntWolfCollarColorIndexValueRemapper extends NetworkEntityMetadataObjectIndexValueTransformer<NetworkEntityMetadataObjectVarInt> {

		public VarIntWolfCollarColorIndexValueRemapper(int toIndex) {
			super(NetworkEntityMetadataObjectIndex.Wolf.COLLAR_COLOR, toIndex);
		}

		@Override
		public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectVarInt object) {
			return new NetworkEntityMetadataObjectVarInt(15 - object.getValue());
		}

	}

}
