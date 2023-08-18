package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_5__7;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleScoreboardScore;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;
import protocolsupport.utils.MiscUtils;

public class ScoreboardScore extends MiddleScoreboardScore implements
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6,
IClientboundMiddlePacketV7 {

	public ScoreboardScore(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData scoreboardscore = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SCOREBOARD_SCORE);
		StringCodec.writeString(scoreboardscore, version, MiscUtils.clampString(name, 16));
		scoreboardscore.writeByte(mode);
		if (mode != 1) {
			StringCodec.writeString(scoreboardscore, version, objectiveName);
			scoreboardscore.writeInt(value);
		}
		io.writeClientbound(scoreboardscore);
	}

}
