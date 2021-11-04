package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_14r1_14r2;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV14r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV14r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.AbstractLegacyStartGame;
import protocolsupport.protocol.typeremapper.legacy.LegacyDimension;

public class StartGame extends AbstractLegacyStartGame implements
IClientboundMiddlePacketV14r1,
IClientboundMiddlePacketV14r2 {

	public StartGame(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeStartGame(int dimensionId) {
		ClientBoundPacketData startgame = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_START_GAME);
		startgame.writeInt(player.getId());
		startgame.writeByte(gamemodeCurrent.getId() | (hardcore ? 0x8 : 0));
		startgame.writeInt(dimensionId);
		startgame.writeByte(maxplayers);
		StringCodec.writeVarIntUTF8String(startgame, LegacyDimension.getWorldType(worldFlat));
		VarNumberCodec.writeVarInt(startgame, renderDistance);
		startgame.writeBoolean(reducedDebugInfo);
		io.writeClientbound(startgame);
	}

}
