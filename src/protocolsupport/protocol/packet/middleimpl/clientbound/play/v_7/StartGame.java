package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleStartGame;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.types.Difficulty;

public class StartGame extends MiddleStartGame {

	public StartGame(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData startgame = codec.allocClientBoundPacketData(PacketType.CLIENTBOUND_PLAY_START_GAME);
		startgame.writeInt(player.getId());
		startgame.writeByte(gamemode.getId() | (hardcore ? 0x8 : 0));
		startgame.writeByte(dimension.getId());
		MiscSerializer.writeByteEnum(startgame, Difficulty.HARD);
		startgame.writeByte(Math.min(maxplayers, 60));
		StringSerializer.writeVarIntUTF8String(startgame, leveltype);
		codec.write(startgame);
	}

}
