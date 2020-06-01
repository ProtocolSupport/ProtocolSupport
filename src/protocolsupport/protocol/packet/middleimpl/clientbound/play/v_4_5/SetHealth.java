package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSetHealth;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleClientCommand;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.storage.netcache.AttributesCache;

public class SetHealth extends MiddleSetHealth {

	protected final AttributesCache clientCache = cache.getAttributesCache();

	public SetHealth(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData sethealth = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SET_HEALTH);
		sethealth.writeShort((int) Math.ceil((health * 20.0F) / cache.getAttributesCache().getMaxHealth()));
		sethealth.writeShort(food);
		sethealth.writeFloat(saturation);
		codec.write(sethealth);

		if ((health <= 0) && !clientCache.isRespawnScreenEnabled()) {
			codec.readAndComplete(MiddleClientCommand.create(MiddleClientCommand.Command.REQUEST_RESPAWN));
		}
	}

}
