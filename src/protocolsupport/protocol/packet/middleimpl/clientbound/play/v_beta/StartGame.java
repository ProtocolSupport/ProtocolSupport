package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_beta;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleStartGame;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;

public class StartGame extends MiddleStartGame {

	public StartGame(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData startgame = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_START_GAME);
		startgame.writeInt(player.getId());
		StringSerializer.writeShortUTF16BEString(startgame, ""); //unused
		startgame.writeLong(0); //seed
		startgame.writeByte(ChangeDimension.remapDimension(dimension).getId());
		codec.write(startgame);
	}

}
