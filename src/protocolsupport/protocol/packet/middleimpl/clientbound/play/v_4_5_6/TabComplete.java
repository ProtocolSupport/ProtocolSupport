package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6;

import java.util.Arrays;
import java.util.stream.Collectors;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleTabComplete;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.utils.Utils;

public class TabComplete extends MiddleTabComplete {

	public TabComplete(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		String prefix = start <= 1 ? "/" : "";
		ClientBoundPacketData tabcomplete = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_TAB_COMPLETE);
		StringSerializer.writeShortUTF16BEString(tabcomplete, Utils.clampString(String.join("\u0000", Arrays.stream(matches).map(input -> prefix + input.getMatch()).collect(Collectors.toList())), Short.MAX_VALUE));
		codec.write(tabcomplete);
	}

}
