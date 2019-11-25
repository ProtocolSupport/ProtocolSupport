package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_6_7;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSetHealth;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class SetHealth extends MiddleSetHealth {

	public SetHealth(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData sethealth = codec.allocClientBoundPacketData(PacketType.CLIENTBOUND_PLAY_SET_HEALTH);
		sethealth.writeFloat(health);
		sethealth.writeShort(food);
		sethealth.writeFloat(saturation);
		codec.write(sethealth);
	}

}
