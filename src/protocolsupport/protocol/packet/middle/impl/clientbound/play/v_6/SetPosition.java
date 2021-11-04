package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_6;

import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8.AbstractLegacyTeleportConfirmSetPosition;

public class SetPosition extends AbstractLegacyTeleportConfirmSetPosition implements IClientboundMiddlePacketV6 {

	public SetPosition(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		y += +1.6200000047683716D;
		ClientBoundPacketData setposition = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_POSITION);
		setposition.writeDouble(x);
		setposition.writeDouble(y);
		setposition.writeDouble(y + 1.6200000047683716D);
		setposition.writeDouble(z);
		setposition.writeFloat(yaw);
		setposition.writeFloat(pitch);
		setposition.writeBoolean(false);
		io.writeClientbound(setposition);
	}

}
