package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_20;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.codec.chat.ChatCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleCombatDeath;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV20;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class CombatDeath extends MiddleCombatDeath implements
IClientboundMiddlePacketV20 {

	public CombatDeath(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		ClientBoundPacketData combatdeathPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_COMBAT_DEATH);
		VarNumberCodec.writeVarInt(combatdeathPacket, playerId);
		StringCodec.writeVarIntUTF8String(combatdeathPacket, ChatCodec.serialize(version, clientCache.getLocale(), message));
		io.writeClientbound(combatdeathPacket);
	}

}
