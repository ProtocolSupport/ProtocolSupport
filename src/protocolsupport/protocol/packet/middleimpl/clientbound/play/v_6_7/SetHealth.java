package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_6_7;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSetHealth;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleClientCommand;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
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
		sethealth.writeShort(food);
		sethealth.writeFloat(saturation);
		codec.writeClientbound(sethealth);

		if ((health <= 0) && !clientCache.isRespawnScreenEnabled()) {
			codec.writeServerboundAndFlush(MiddleClientCommand.create(MiddleClientCommand.Command.REQUEST_RESPAWN));
		}
	}

}
