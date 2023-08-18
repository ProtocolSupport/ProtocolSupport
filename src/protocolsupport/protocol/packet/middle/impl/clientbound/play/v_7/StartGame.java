package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_7;

import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__13.AbstractChunkCacheStartGame;
import protocolsupport.protocol.typeremapper.legacy.LegacyDimension;
import protocolsupport.protocol.types.Difficulty;

public class StartGame extends AbstractChunkCacheStartGame implements IClientboundMiddlePacketV7 {

	public StartGame(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeStartGame(int dimensionId) {
		ClientBoundPacketData startgame = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_START_GAME);
		startgame.writeInt(player.getId());
		startgame.writeByte(gamemodeCurrent.getId() | (hardcore ? 0x8 : 0));
		startgame.writeByte(dimensionId);
		MiscDataCodec.writeByteEnum(startgame, Difficulty.HARD);
		startgame.writeByte(Math.min(maxplayers, 60));
		StringCodec.writeVarIntUTF8String(startgame, LegacyDimension.getWorldType(worldFlat));
		io.writeClientbound(startgame);
	}

}
