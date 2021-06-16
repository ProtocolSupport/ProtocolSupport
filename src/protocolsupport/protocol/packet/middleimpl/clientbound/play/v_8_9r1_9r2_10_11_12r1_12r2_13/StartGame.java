package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractChunkCacheStartGame;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyDimension;
import protocolsupport.protocol.types.Difficulty;

public class StartGame extends AbstractChunkCacheStartGame {

	public StartGame(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeStartGame(int dimensionId) {
		ClientBoundPacketData startgame = ClientBoundPacketData.create(ClientBoundPacketType.CLIENTBOUND_PLAY_START_GAME);
		startgame.writeInt(player.getId());
		startgame.writeByte(gamemodeCurrent.getId() | (hardcore ? 0x8 : 0));
		if (version.isBefore(ProtocolVersion.MINECRAFT_1_9_1)) {
			startgame.writeByte(dimensionId);
		} else {
			startgame.writeInt(dimensionId);
		}
		MiscSerializer.writeByteEnum(startgame, Difficulty.HARD);
		startgame.writeByte(maxplayers);
		StringSerializer.writeVarIntUTF8String(startgame, LegacyDimension.getWorldType(worldFlat));
		startgame.writeBoolean(reducedDebugInfo);
		codec.writeClientbound(startgame);
	}

}
