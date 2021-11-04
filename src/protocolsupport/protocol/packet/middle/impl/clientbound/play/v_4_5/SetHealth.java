package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5;

import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleSetHealth;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleClientCommand;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class SetHealth extends MiddleSetHealth implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5
{

	protected final ClientCache clientCache = cache.getClientCache();

	public SetHealth(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData sethealth = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SET_HEALTH);
		sethealth.writeShort((int) Math.ceil((health * 20.0F) / cache.getClientCache().getMaxHealth()));
		sethealth.writeShort(food);
		sethealth.writeFloat(saturation);
		io.writeClientbound(sethealth);

		if ((health <= 0) && !clientCache.isRespawnScreenEnabled()) {
			io.writeServerboundAndFlush(MiddleClientCommand.create(MiddleClientCommand.Command.REQUEST_RESPAWN));
		}
	}

}
