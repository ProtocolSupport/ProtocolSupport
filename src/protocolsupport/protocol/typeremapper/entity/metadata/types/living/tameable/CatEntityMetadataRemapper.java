package protocolsupport.protocol.typeremapper.entity.metadata.types.living.tameable;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.TameableEntityMetadataRemapper;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class CatEntityMetadataRemapper extends TameableEntityMetadataRemapper {

	public static final CatEntityMetadataRemapper INSTANCE = new CatEntityMetadataRemapper();

	public CatEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Cat.VARIANT, 18), ProtocolVersionsHelper.UP_1_15);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Cat.VARIANT, 17), ProtocolVersionsHelper.ALL_1_14);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Cat.UNKNOWN_1, 19), ProtocolVersionsHelper.UP_1_15);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Cat.UNKNOWN_1, 18), ProtocolVersionsHelper.ALL_1_14);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Cat.UNKNOWN_2, 20), ProtocolVersionsHelper.UP_1_15);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Cat.UNKNOWN_2, 19), ProtocolVersionsHelper.ALL_1_14);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Cat.COLLAR_COLOR, 21), ProtocolVersionsHelper.UP_1_15);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Cat.COLLAR_COLOR, 20), ProtocolVersionsHelper.ALL_1_14);

		addRemap(new IndexValueRemapper<NetworkEntityMetadataObjectVarInt>(NetworkEntityMetadataObjectIndex.Cat.VARIANT, 15) {
			@Override
			public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObjectVarInt object) {
				return new NetworkEntityMetadataObjectVarInt(getLegacyCatVariant(object.getValue()));
			}
		}, ProtocolVersionsHelper.RANGE__1_10__1_13_2);

		addRemap(new IndexValueRemapper<NetworkEntityMetadataObjectVarInt>(NetworkEntityMetadataObjectIndex.Cat.VARIANT, 14) {
			@Override
			public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObjectVarInt object) {
				return new NetworkEntityMetadataObjectVarInt(getLegacyCatVariant(object.getValue()));
			}
		}, ProtocolVersionsHelper.ALL_1_9);

		addRemap(new IndexValueRemapper<NetworkEntityMetadataObjectVarInt>(NetworkEntityMetadataObjectIndex.Cat.VARIANT, 18) {
			@Override
			public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObjectVarInt object) {
				return new NetworkEntityMetadataObjectByte((byte) getLegacyCatVariant(object.getValue()));
			}
		}, ProtocolVersionsHelper.BEFORE_1_9);

	}

	private int getLegacyCatVariant(int currentType){
		switch (currentType){
			case 10: return 1;
			case 0: case 5: case 6: return 2;
			case 4: case 7: case 8: case 9: return 3;
			default: return currentType;
		}
	}

}
