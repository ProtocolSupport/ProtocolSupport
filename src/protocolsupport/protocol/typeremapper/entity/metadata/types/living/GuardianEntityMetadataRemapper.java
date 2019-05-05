package protocolsupport.protocol.typeremapper.entity.metadata.types.living;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.InsentientEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNumberToInt;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectBoolean;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectInt;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class GuardianEntityMetadataRemapper extends InsentientEntityMetadataRemapper {

	public static final GuardianEntityMetadataRemapper INSTANCE = new GuardianEntityMetadataRemapper();

	public GuardianEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Guardian.SPIKES, 14), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Guardian.SPIKES, 12), ProtocolVersionsHelper.RANGE__1_11__1_13_2);
		addRemap(new IndexValueRemapper<NetworkEntityMetadataObjectBoolean>(NetworkEntityMetadataObjectIndex.Guardian.SPIKES, 12) {
			@Override
			public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObjectBoolean object) {
				return new NetworkEntityMetadataObjectByte(object.getValue() ? (byte) 2 : (byte) 0);
			}
		}, ProtocolVersion.MINECRAFT_1_10);
		addRemap(new IndexValueRemapper<NetworkEntityMetadataObjectBoolean>(NetworkEntityMetadataObjectIndex.Guardian.SPIKES, 11) {
			@Override
			public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObjectBoolean object) {
				return new NetworkEntityMetadataObjectByte(object.getValue() ? (byte) 2 : (byte) 0);
			}
		}, ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapper<NetworkEntityMetadataObjectBoolean>(NetworkEntityMetadataObjectIndex.Guardian.SPIKES, 16) {
			@Override
			public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObjectBoolean object) {
				return new NetworkEntityMetadataObjectInt(object.getValue() ? (byte) 2 : (byte) 0);
			}
		}, ProtocolVersion.MINECRAFT_1_8);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Guardian.TARGET_ID, 15), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Guardian.TARGET_ID, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Guardian.TARGET_ID, 12), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNumberToInt(NetworkEntityMetadataObjectIndex.Guardian.TARGET_ID, 17), ProtocolVersion.MINECRAFT_1_8);
	}

}
