package protocolsupport.protocol.typeremapper.entity.metadata.types.living.horse;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.metadata.object.misc.NetworkEntityMetadataFirstUpdateObjectAddRemapper;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class LegacyDonkeyEntityMetadataRemapper extends CargoHorseEntityMetadataRemapper {

	public static final LegacyDonkeyEntityMetadataRemapper INSTANCE = new LegacyDonkeyEntityMetadataRemapper();

	protected LegacyDonkeyEntityMetadataRemapper() {
		addRemap(new NetworkEntityMetadataFirstUpdateObjectAddRemapper(14, new NetworkEntityMetadataObjectVarInt(1)), ProtocolVersion.MINECRAFT_1_10);
		addRemap(new NetworkEntityMetadataFirstUpdateObjectAddRemapper(13, new NetworkEntityMetadataObjectVarInt(1)), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new NetworkEntityMetadataFirstUpdateObjectAddRemapper(19, new NetworkEntityMetadataObjectByte((byte) 1)), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_6_1, ProtocolVersion.MINECRAFT_1_8));
	}

}
