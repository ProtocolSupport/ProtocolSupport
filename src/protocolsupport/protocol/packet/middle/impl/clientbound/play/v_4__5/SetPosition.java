package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__5;

import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__8.AbstractLegacyTeleportConfirmSetPosition;

public class SetPosition extends AbstractLegacyTeleportConfirmSetPosition implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5
{

	public SetPosition(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData setposition = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_POSITION);
		setposition.writeDouble(x);
		setposition.writeDouble(y + 1.6200000047683716D);
		setposition.writeDouble(y);
		setposition.writeDouble(z);
		setposition.writeFloat(yaw);
		setposition.writeFloat(pitch);
		setposition.writeBoolean(false);
		io.writeClientbound(setposition);
	}

}
