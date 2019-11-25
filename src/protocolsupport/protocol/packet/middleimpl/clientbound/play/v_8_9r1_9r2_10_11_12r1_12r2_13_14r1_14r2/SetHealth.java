package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSetHealth;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class SetHealth extends MiddleSetHealth {

	public SetHealth(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData sethealth = codec.allocClientBoundPacketData(PacketType.CLIENTBOUND_PLAY_SET_HEALTH);
		sethealth.writeFloat(health);
		VarNumberSerializer.writeVarInt(sethealth, food);
		sethealth.writeFloat(saturation);
		codec.write(sethealth);
	}

}
