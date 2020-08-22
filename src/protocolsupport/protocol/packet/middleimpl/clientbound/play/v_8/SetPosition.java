package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8.AbstractSetPosition;

public class SetPosition extends AbstractSetPosition {

	public SetPosition(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData setposition = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_POSITION);
		setposition.writeDouble(x);
		setposition.writeDouble(y);
		setposition.writeDouble(z);
		setposition.writeFloat(yaw);
		setposition.writeFloat(pitch);
		setposition.writeByte(0);
		codec.write(setposition);
	}

}
