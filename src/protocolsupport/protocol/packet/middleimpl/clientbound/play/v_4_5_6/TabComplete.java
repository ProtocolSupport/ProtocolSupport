package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleTabComplete;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TabComplete extends MiddleTabComplete {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_TAB_COMPLETE_ID);
		String test = Utils.clampString(String.join("\u0000", Arrays.stream(matches).map((input) -> (start == 0 ? "/" : "") + input.getMatch()).collect(Collectors.toList())), Short.MAX_VALUE);
		StringSerializer.writeString(serializer, version, Utils.clampString(String.join("\u0000", Arrays.stream(matches).map((input) -> (start == 0 ? "/" : "") + input.getMatch()).collect(Collectors.toList())), Short.MAX_VALUE));
		return RecyclableSingletonList.create(serializer);
	}

}
