package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13_14r1_14r2_15_16r1_16r2_17;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.chat.ChatCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleScoreboardTeam;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class ScoreboardTeam extends MiddleScoreboardTeam {

	public ScoreboardTeam(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		String locale = clientCache.getLocale();

		ClientBoundPacketData scoreboardteam = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SCOREBOARD_TEAM);
		StringCodec.writeVarIntUTF8String(scoreboardteam, name);
		MiscDataCodec.writeByteEnum(scoreboardteam, mode);
		if ((mode == Mode.CREATE) || (mode == Mode.UPDATE)) {
			StringCodec.writeVarIntUTF8String(scoreboardteam, ChatCodec.serialize(version, locale, displayName));
			scoreboardteam.writeByte(friendlyFire);
			StringCodec.writeVarIntUTF8String(scoreboardteam, nameTagVisibility);
			StringCodec.writeVarIntUTF8String(scoreboardteam, collisionRule);
			MiscDataCodec.writeVarIntEnum(scoreboardteam, format);
			StringCodec.writeVarIntUTF8String(scoreboardteam, ChatCodec.serialize(version, locale, prefix));
			StringCodec.writeVarIntUTF8String(scoreboardteam, ChatCodec.serialize(version, locale, suffix));
		}
		if ((mode == Mode.CREATE) || (mode == Mode.PLAYERS_ADD) || (mode == Mode.PLAYERS_REMOVE)) {
			ArrayCodec.writeVarIntVarIntUTF8StringArray(scoreboardteam, players);
		}
		codec.writeClientbound(scoreboardteam);
	}

}
