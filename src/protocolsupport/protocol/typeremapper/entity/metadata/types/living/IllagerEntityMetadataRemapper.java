package protocolsupport.protocol.typeremapper.entity.metadata.types.living;

import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.RaidParticipantEntityMetadataRemapper;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.BitUtils;

public class IllagerEntityMetadataRemapper extends RaidParticipantEntityMetadataRemapper {

	public static final IllagerEntityMetadataRemapper INSTANCE = new IllagerEntityMetadataRemapper();

	protected IllagerEntityMetadataRemapper() {
		addRemap(
			new IndexValueRemapper<NetworkEntityMetadataObjectByte>(NetworkEntityMetadataObjectIndex.Insentient.INS_FLAGS, 12) {
				@Override
				public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObjectByte object) {
					return new NetworkEntityMetadataObjectByte((byte) (BitUtils.isIBitSet(object.getValue(), NetworkEntityMetadataObjectIndex.Insentient.INS_FLAGS_BIT_ATTACKING) ? 1 : 0));
				}
			},
			ProtocolVersionsHelper.RANGE__1_11__1_13_2
		);
	}

}
