package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_8__13;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV10;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV11;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV13;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__13.AbstractChunkCacheStartGame;
import protocolsupport.protocol.typeremapper.legacy.LegacyDimension;
import protocolsupport.protocol.types.Difficulty;

public class StartGame extends AbstractChunkCacheStartGame implements
IClientboundMiddlePacketV8,
IClientboundMiddlePacketV9r1,
IClientboundMiddlePacketV9r2,
IClientboundMiddlePacketV10,
IClientboundMiddlePacketV11,
IClientboundMiddlePacketV12r1,
IClientboundMiddlePacketV12r2,
IClientboundMiddlePacketV13 {

	public StartGame(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeStartGame(int dimensionId) {
		ClientBoundPacketData startgame = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_START_GAME);
		startgame.writeInt(player.getId());
		startgame.writeByte(gamemodeCurrent.getId() | (hardcore ? 0x8 : 0));
		if (version.isBefore(ProtocolVersion.MINECRAFT_1_9_1)) {
			startgame.writeByte(dimensionId);
		} else {
			startgame.writeInt(dimensionId);
		}
		MiscDataCodec.writeByteEnum(startgame, Difficulty.HARD);
		startgame.writeByte(maxplayers);
		StringCodec.writeVarIntUTF8String(startgame, LegacyDimension.getWorldType(worldFlat));
		startgame.writeBoolean(reducedDebugInfo);
		io.writeClientbound(startgame);
	}

}
