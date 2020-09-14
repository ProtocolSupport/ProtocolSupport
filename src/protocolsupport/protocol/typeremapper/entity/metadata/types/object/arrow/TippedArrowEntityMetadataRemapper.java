package protocolsupport.protocol.typeremapper.entity.metadata.types.object.arrow;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.NetworkEntityMetadataIndexValueRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.NetworkEntityMetadataIndexValueRemapperNoOp;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class TippedArrowEntityMetadataRemapper extends ArrowEntityMetadataRemapper {

	public static final TippedArrowEntityMetadataRemapper INSTANCE = new TippedArrowEntityMetadataRemapper();

	protected TippedArrowEntityMetadataRemapper() {
		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.TippedArrow.COLOR, 9), ProtocolVersionsHelper.UP_1_16);
		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.TippedArrow.COLOR, 10), ProtocolVersionsHelper.RANGE__1_14__1_15_2);
		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.TippedArrow.COLOR, 8), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_13_1, ProtocolVersion.MINECRAFT_1_13_2));
		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.TippedArrow.COLOR, 7), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_11_1, ProtocolVersion.MINECRAFT_1_13));
		addRemap(new NetworkEntityMetadataIndexValueRemapper<NetworkEntityMetadataObjectVarInt>(NetworkEntityMetadataObjectIndex.TippedArrow.COLOR, 7) {
			@Override
			public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObjectVarInt object) {
				int color = object.getValue();
				return new NetworkEntityMetadataObjectVarInt(color == -1 ? 0 : color);
			}
		}, ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_10, ProtocolVersion.MINECRAFT_1_11));
		addRemap(new NetworkEntityMetadataIndexValueRemapper<NetworkEntityMetadataObjectVarInt>(NetworkEntityMetadataObjectIndex.TippedArrow.COLOR, 6) {
			@Override
			public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObjectVarInt object) {
				int color = object.getValue();
				return new NetworkEntityMetadataObjectVarInt(color == -1 ? 0 : color);
			}
		}, ProtocolVersionsHelper.ALL_1_9);
	}

}
