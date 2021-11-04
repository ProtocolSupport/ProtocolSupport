package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2;

import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleScoreboardObjective;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV10;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV11;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r2;
import protocolsupport.protocol.typeremapper.legacy.LegacyChat;

public class ScoreboardObjective extends MiddleScoreboardObjective implements
IClientboundMiddlePacketV8,
IClientboundMiddlePacketV9r1,
IClientboundMiddlePacketV9r2,
IClientboundMiddlePacketV10,
IClientboundMiddlePacketV11,
IClientboundMiddlePacketV12r1,
IClientboundMiddlePacketV12r2 {

	public ScoreboardObjective(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData scoreboardobjective = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SCOREBOARD_OBJECTIVE);
		StringCodec.writeVarIntUTF8String(scoreboardobjective, name);
		MiscDataCodec.writeByteEnum(scoreboardobjective, mode);
		if (mode != Mode.REMOVE) {
			StringCodec.writeVarIntUTF8String(scoreboardobjective, LegacyChat.clampLegacyText(value.toLegacyText(cache.getClientCache().getLocale()), 32));
			StringCodec.writeVarIntUTF8String(scoreboardobjective, getTypeString(type));
		}
		io.writeClientbound(scoreboardobjective);
	}

	protected static String getTypeString(Type type) {
		switch (type) {
			case HEARTS: {
				return "hearts";
			}
			case INTEGER:
			default: {
				return "integer";
			}
		}
	}

}
