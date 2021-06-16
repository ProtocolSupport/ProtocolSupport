package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13_14r1_14r2_15_16r1_16r2_17;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleScoreboardObjective;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.chat.ChatSerializer;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class ScoreboardObjective extends MiddleScoreboardObjective {

	public ScoreboardObjective(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		ClientBoundPacketData scoreboardobjective = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SCOREBOARD_OBJECTIVE);
		StringSerializer.writeVarIntUTF8String(scoreboardobjective, name);
		scoreboardobjective.writeByte(mode.ordinal());
		if (mode != Mode.REMOVE) {
			StringSerializer.writeVarIntUTF8String(scoreboardobjective, ChatSerializer.serialize(version, clientCache.getLocale(), value));
			MiscSerializer.writeVarIntEnum(scoreboardobjective, type);
		}
		codec.writeClientbound(scoreboardobjective);
	}

}
