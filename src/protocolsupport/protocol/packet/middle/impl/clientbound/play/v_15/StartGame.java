package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_15;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV15;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__15.AbstractLegacyStartGame;
import protocolsupport.protocol.typeremapper.legacy.LegacyDimension;

public class StartGame extends AbstractLegacyStartGame implements IClientboundMiddlePacketV15 {

	public StartGame(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeStartGame(int dimensionId) {
		ClientBoundPacketData startgame = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_START_GAME);
		startgame.writeInt(player.getId());
		startgame.writeByte(gamemodeCurrent.getId() | (hardcore ? 0x8 : 0));
		startgame.writeInt(dimensionId);
		startgame.writeLong(hashedSeed);
		startgame.writeByte(maxplayers);
		StringCodec.writeVarIntUTF8String(startgame, LegacyDimension.getWorldType(worldFlat));
		VarNumberCodec.writeVarInt(startgame, renderDistance);
		startgame.writeBoolean(reducedDebugInfo);
		startgame.writeBoolean(respawnScreenEnabled);
		io.writeClientbound(startgame);
	}

}
