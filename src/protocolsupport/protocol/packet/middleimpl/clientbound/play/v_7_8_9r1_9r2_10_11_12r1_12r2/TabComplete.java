package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7_8_9r1_9r2_10_11_12r1_12r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleTabComplete;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.StringSerializer;

public class TabComplete extends MiddleTabComplete {

	public TabComplete(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		String prefix = start <= 1 ? "/" : "";
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_TAB_COMPLETE);
		ArraySerializer.writeVarIntTArray(serializer, matches, (to, match) -> StringSerializer.writeVarIntUTF8String(to, prefix + match.getMatch()));
		codec.write(serializer);
	}

}
