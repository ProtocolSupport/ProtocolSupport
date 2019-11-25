package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleStartGame;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class StartGame extends MiddleStartGame {

	public StartGame(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData startgame = codec.allocClientBoundPacketData(PacketType.CLIENTBOUND_PLAY_START_GAME);
		startgame.writeInt(player.getId());
		startgame.writeByte(gamemode.getId() | (hardcore ? 0x8 : 0));
		startgame.writeInt(dimension.getId());
		startgame.writeByte(maxplayers);
		StringSerializer.writeVarIntUTF8String(startgame, leveltype);
		VarNumberSerializer.writeVarInt(startgame, renderDistance);
		startgame.writeBoolean(reducedDebugInfo);
		codec.write(startgame);
	}

}
