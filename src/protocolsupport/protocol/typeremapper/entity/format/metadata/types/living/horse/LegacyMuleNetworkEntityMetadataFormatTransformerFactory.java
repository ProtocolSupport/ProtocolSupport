package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.horse;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.misc.NetworkEntityMetadataObjectAddOnFirstUpdateTransformer;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class LegacyMuleNetworkEntityMetadataFormatTransformerFactory extends CargoHorseNetworkEntityMetadataFormatTransformerFactory {

	public static final LegacyMuleNetworkEntityMetadataFormatTransformerFactory INSTANCE = new LegacyMuleNetworkEntityMetadataFormatTransformerFactory();

	protected LegacyMuleNetworkEntityMetadataFormatTransformerFactory() {
		//legacy horse type
		add(new NetworkEntityMetadataObjectAddOnFirstUpdateTransformer(14, new NetworkEntityMetadataObjectVarInt(2)), ProtocolVersion.MINECRAFT_1_10);
		add(new NetworkEntityMetadataObjectAddOnFirstUpdateTransformer(13, new NetworkEntityMetadataObjectVarInt(2)), ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectAddOnFirstUpdateTransformer(19, new NetworkEntityMetadataObjectByte((byte) 2)), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_6_1, ProtocolVersion.MINECRAFT_1_8));
	}

}
