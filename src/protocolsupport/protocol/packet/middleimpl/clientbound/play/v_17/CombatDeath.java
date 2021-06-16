package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_17;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCombatDeath;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.serializer.chat.ChatSerializer;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class CombatDeath extends MiddleCombatDeath {

	public CombatDeath(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		ClientBoundPacketData combatdeathPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_COMBAT_DEATH);
		VarNumberSerializer.writeVarInt(combatdeathPacket, playerId);
		combatdeathPacket.writeInt(killerId);
		StringSerializer.writeVarIntUTF8String(combatdeathPacket, ChatSerializer.serialize(version, clientCache.getLocale(), message));
		codec.writeClientbound(combatdeathPacket);
	}

}
