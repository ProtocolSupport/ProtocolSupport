package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13_14r1_14r2_15_16r1_16r2_17;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleLookAt;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class LookAt extends MiddleLookAt {

	public LookAt(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData lookat = ClientBoundPacketData.create(ClientBoundPacketType.CLIENTBOUND_PLAY_LOOK_AT);
		lookat.writeBytes(data);
		codec.writeClientbound(lookat);
	}

}
