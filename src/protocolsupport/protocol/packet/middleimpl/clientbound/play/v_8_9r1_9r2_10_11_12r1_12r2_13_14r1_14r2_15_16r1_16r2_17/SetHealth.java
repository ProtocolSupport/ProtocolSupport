package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSetHealth;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleClientCommand;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class SetHealth extends MiddleSetHealth {

	protected final ClientCache clientCache = cache.getClientCache();

	public SetHealth(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData sethealth = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SET_HEALTH);
		sethealth.writeFloat(health);
		VarNumberSerializer.writeVarInt(sethealth, food);
		sethealth.writeFloat(saturation);
		codec.writeClientbound(sethealth);

		if ((health <= 0) && !clientCache.isRespawnScreenEnabled() && version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_14_4)) {
			codec.writeServerboundAndFlush(MiddleClientCommand.create(MiddleClientCommand.Command.REQUEST_RESPAWN));
		}
	}

}
