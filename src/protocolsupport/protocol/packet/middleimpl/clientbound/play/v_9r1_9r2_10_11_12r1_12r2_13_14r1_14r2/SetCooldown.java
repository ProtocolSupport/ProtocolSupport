package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSetCooldown;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class SetCooldown extends MiddleSetCooldown {

	public SetCooldown(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData setcooldown = codec.allocClientBoundPacketData(PacketType.CLIENTBOUND_PLAY_SET_COOLDOWN);
		VarNumberSerializer.writeVarInt(setcooldown, itemId);
		VarNumberSerializer.writeVarInt(setcooldown, cooldown);
		codec.write(setcooldown);
	}

}
