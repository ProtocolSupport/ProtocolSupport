package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddlePlayerListHeaderFooter;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;

public class PlayerListHeaderFooter extends MiddlePlayerListHeaderFooter {

	public PlayerListHeaderFooter(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData playerlistheaderfooter = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_PLAYER_LIST_HEADER_FOOTER);
		StringSerializer.writeVarIntUTF8String(playerlistheaderfooter, headerJson);
		StringSerializer.writeVarIntUTF8String(playerlistheaderfooter, footerJson);
		codec.write(playerlistheaderfooter);
	}

}
