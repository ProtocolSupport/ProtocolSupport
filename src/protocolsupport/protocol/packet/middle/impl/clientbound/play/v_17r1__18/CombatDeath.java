package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_17r1__18;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.codec.chat.ChatCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleCombatDeath;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV18;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class CombatDeath extends MiddleCombatDeath implements
IClientboundMiddlePacketV17r1,
IClientboundMiddlePacketV17r2,
IClientboundMiddlePacketV18 {

	public CombatDeath(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		ClientBoundPacketData combatdeathPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_COMBAT_DEATH);
		VarNumberCodec.writeVarInt(combatdeathPacket, playerId);
		combatdeathPacket.writeInt(0); //killer id
		StringCodec.writeVarIntUTF8String(combatdeathPacket, ChatCodec.serialize(version, clientCache.getLocale(), message));
		io.writeClientbound(combatdeathPacket);
	}

}
