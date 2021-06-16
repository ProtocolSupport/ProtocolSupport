package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_6;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8.AbstractLegacyTeleportConfirmSetPosition;

public class SetPosition extends AbstractLegacyTeleportConfirmSetPosition {

	public SetPosition(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		y += +1.6200000047683716D;
		ClientBoundPacketData setposition = ClientBoundPacketData.create(ClientBoundPacketType.CLIENTBOUND_PLAY_POSITION);
		setposition.writeDouble(x);
		setposition.writeDouble(y);
		setposition.writeDouble(y + 1.6200000047683716D);
		setposition.writeDouble(z);
		setposition.writeFloat(yaw);
		setposition.writeFloat(pitch);
		setposition.writeBoolean(false);
		codec.writeClientbound(setposition);
	}

}
