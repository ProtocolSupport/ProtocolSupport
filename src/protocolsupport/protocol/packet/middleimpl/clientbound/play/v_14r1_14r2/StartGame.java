package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.AbstractLegacyStartGame;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyDimension;

public class StartGame extends AbstractLegacyStartGame {

	public StartGame(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeStartGame(int dimensionId) {
		ClientBoundPacketData startgame = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_START_GAME);
		startgame.writeInt(player.getId());
		startgame.writeByte(gamemodeCurrent.getId() | (hardcore ? 0x8 : 0));
		startgame.writeInt(dimensionId);
		startgame.writeByte(maxplayers);
		StringSerializer.writeVarIntUTF8String(startgame, LegacyDimension.getWorldType(worldFlat));
		VarNumberSerializer.writeVarInt(startgame, renderDistance);
		startgame.writeBoolean(reducedDebugInfo);
		codec.write(startgame);
	}

}
