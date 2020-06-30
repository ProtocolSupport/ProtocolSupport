package protocolsupport.protocol.typeremapper.entity.metadata.types.living.tameable;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapperBooleanToByte;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapperNumberToByte;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.TameableEntityMetadataRemapper;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class WolfEntityMetadataRemapper extends TameableEntityMetadataRemapper {

	public static final WolfEntityMetadataRemapper INSTANCE = new WolfEntityMetadataRemapper();

	protected WolfEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Wolf.BEGGING, 18), ProtocolVersionsHelper.UP_1_15);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Wolf.BEGGING, 18), ProtocolVersionsHelper.ALL_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Wolf.BEGGING, 16), ProtocolVersionsHelper.RANGE__1_11__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Wolf.BEGGING, 16), ProtocolVersion.MINECRAFT_1_10);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Wolf.BEGGING, 15), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperBooleanToByte(NetworkEntityMetadataObjectIndex.Wolf.BEGGING, 19), ProtocolVersionsHelper.DOWN_1_8);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Wolf.COLLAR_COLOR, 19), ProtocolVersionsHelper.UP_1_15);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Wolf.COLLAR_COLOR, 19), ProtocolVersionsHelper.ALL_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Wolf.COLLAR_COLOR, 17), ProtocolVersionsHelper.ALL_1_13);
		addRemap(new VarIntWolfCollarColorIndexValueRemapper(17), ProtocolVersionsHelper.RANGE__1_10__1_12_2);
		addRemap(new VarIntWolfCollarColorIndexValueRemapper(16), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapper<NetworkEntityMetadataObjectVarInt>(NetworkEntityMetadataObjectIndex.Wolf.COLLAR_COLOR, 20) {
			@Override
			public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObjectVarInt object) {
				return new NetworkEntityMetadataObjectByte((byte) (15 - object.getValue()));
			}
		}, ProtocolVersion.MINECRAFT_1_8);
		addRemap(new IndexValueRemapperNumberToByte(NetworkEntityMetadataObjectIndex.Wolf.COLLAR_COLOR, 20), ProtocolVersionsHelper.DOWN_1_7_10);
	}

	protected static class VarIntWolfCollarColorIndexValueRemapper extends IndexValueRemapper<NetworkEntityMetadataObjectVarInt> {

		public VarIntWolfCollarColorIndexValueRemapper(int toIndex) {
			super(NetworkEntityMetadataObjectIndex.Wolf.COLLAR_COLOR, toIndex);
		}

		@Override
		public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObjectVarInt object) {
			return new NetworkEntityMetadataObjectVarInt(15 - object.getValue());
		}

	}

}
