package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_5__7;

import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleScoreboardObjective;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;
import protocolsupport.protocol.typeremapper.legacy.LegacyChat;

public class ScoreboardObjective extends MiddleScoreboardObjective implements
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6,
IClientboundMiddlePacketV7 {

	public ScoreboardObjective(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData scoreboardobjective = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SCOREBOARD_OBJECTIVE);
		StringCodec.writeString(scoreboardobjective, version, name);
		StringCodec.writeString(
			scoreboardobjective, version,
			mode == Mode.REMOVE ? "" : LegacyChat.clampLegacyText(value.toLegacyText(cache.getClientCache().getLocale()), 32)
		);
		MiscDataCodec.writeByteEnum(scoreboardobjective, mode);
		io.writeClientbound(scoreboardobjective);
	}

}
