package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13_14r1_14r2_15_16r1_16r2_17;

import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.chat.ChatCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleScoreboardObjective;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class ScoreboardObjective extends MiddleScoreboardObjective {

	public ScoreboardObjective(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		ClientBoundPacketData scoreboardobjective = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SCOREBOARD_OBJECTIVE);
		StringCodec.writeVarIntUTF8String(scoreboardobjective, name);
		scoreboardobjective.writeByte(mode.ordinal());
		if (mode != Mode.REMOVE) {
			StringCodec.writeVarIntUTF8String(scoreboardobjective, ChatCodec.serialize(version, clientCache.getLocale(), value));
			MiscDataCodec.writeVarIntEnum(scoreboardobjective, type);
		}
		codec.writeClientbound(scoreboardobjective);
	}

}
