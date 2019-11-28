package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13_14r1_14r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleDeclareCommands;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class DeclareCommands extends MiddleDeclareCommands {

	public DeclareCommands(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData declarecommands = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_DECLARE_COMMANDS);
		declarecommands.writeBytes(data);
		codec.write(declarecommands);
	}

}
