package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_8;

import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8.AbstractLegacyTeleportConfirmSetPosition;

public class SetPosition extends AbstractLegacyTeleportConfirmSetPosition implements IClientboundMiddlePacketV8 {

	public SetPosition(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData setposition = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_POSITION);
		setposition.writeDouble(x);
		setposition.writeDouble(y);
		setposition.writeDouble(z);
		setposition.writeFloat(yaw);
		setposition.writeFloat(pitch);
		setposition.writeByte(0);
		io.writeClientbound(setposition);
	}

}
