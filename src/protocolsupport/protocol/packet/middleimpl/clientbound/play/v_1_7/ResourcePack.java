package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_7;

import java.nio.charset.StandardCharsets;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleResourcePack;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class ResourcePack extends MiddleResourcePack<RecyclableCollection<ClientBoundPacketData>> {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_CUSTOM_PAYLOAD_ID, version);
		serializer.writeString("MC|RPack");
		serializer.writeByteArray(url.getBytes(StandardCharsets.UTF_8));
		return RecyclableSingletonList.create(serializer);
	}

}
