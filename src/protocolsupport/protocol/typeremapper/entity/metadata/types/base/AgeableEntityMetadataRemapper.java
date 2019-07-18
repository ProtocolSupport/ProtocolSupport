package protocolsupport.protocol.typeremapper.entity.metadata.types.base;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapperNumberToInt;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectBoolean;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectInt;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class AgeableEntityMetadataRemapper extends InsentientEntityMetadataRemapper {

	public static final AgeableEntityMetadataRemapper INSTANCE = new AgeableEntityMetadataRemapper();

	public AgeableEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Ageable.IS_BABY, 14), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Ageable.IS_BABY, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Ageable.IS_BABY, 11), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapper<NetworkEntityMetadataObjectBoolean>(NetworkEntityMetadataObjectIndex.Ageable.IS_BABY, 12) {
			@Override
			public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObjectBoolean object) {
				return new NetworkEntityMetadataObjectByte((byte) (object.getValue() ? -1 : 0));
			}
		}, ProtocolVersion.MINECRAFT_1_8);
		addRemap(new IndexValueRemapper<NetworkEntityMetadataObjectBoolean>(NetworkEntityMetadataObjectIndex.Ageable.IS_BABY, 12) {
			@Override
			public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObjectBoolean object) {
				return new NetworkEntityMetadataObjectInt((object.getValue() ? -1 : 0));
			}
		}, ProtocolVersionsHelper.BEFORE_1_8);

		addRemap(new IndexValueRemapperNumberToInt(NetworkEntityMetadataObjectIndex.Ageable.AGE_HACK, 12), ProtocolVersionsHelper.RANGE__1_6__1_7);
	}

}
