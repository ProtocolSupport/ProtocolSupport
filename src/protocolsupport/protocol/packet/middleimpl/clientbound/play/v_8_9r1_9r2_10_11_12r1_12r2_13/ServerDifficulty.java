package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleServerDifficulty;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;

public class ServerDifficulty extends MiddleServerDifficulty {

	public ServerDifficulty(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData serverdifficulty = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SERVER_DIFFICULTY);
		MiscSerializer.writeByteEnum(serverdifficulty, difficulty);
		codec.write(serverdifficulty);
	}

}
