package protocolsupport.protocol.typeremapper.entity.metadata.types.living.horse;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.metadata.object.misc.NetworkEntityMetadataFirstUpdateObjectAddRemapper;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class LegacyMuleEntityMetadataRemapper extends CargoHorseEntityMetadataRemapper {

	public static final LegacyMuleEntityMetadataRemapper INSTANCE = new LegacyMuleEntityMetadataRemapper();

	protected LegacyMuleEntityMetadataRemapper() {
		//legacy horse type
		addRemap(new NetworkEntityMetadataFirstUpdateObjectAddRemapper(14, new NetworkEntityMetadataObjectVarInt(2)), ProtocolVersion.MINECRAFT_1_10);
		addRemap(new NetworkEntityMetadataFirstUpdateObjectAddRemapper(13, new NetworkEntityMetadataObjectVarInt(2)), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new NetworkEntityMetadataFirstUpdateObjectAddRemapper(19, new NetworkEntityMetadataObjectByte((byte) 2)), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_6_1, ProtocolVersion.MINECRAFT_1_8));
	}

}
