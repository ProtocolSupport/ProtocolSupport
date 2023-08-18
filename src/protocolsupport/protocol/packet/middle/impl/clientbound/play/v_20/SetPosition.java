package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_20;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleSetPosition;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV20;

public class SetPosition extends MiddleSetPosition implements
IClientboundMiddlePacketV20 {

	public SetPosition(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData setpositionPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_POSITION);
		setpositionPacket.writeDouble(x);
		setpositionPacket.writeDouble(y);
		setpositionPacket.writeDouble(z);
		setpositionPacket.writeFloat(yaw);
		setpositionPacket.writeFloat(pitch);
		setpositionPacket.writeByte(flags);
		VarNumberCodec.writeVarInt(setpositionPacket, teleportConfirmId);
		io.writeClientbound(setpositionPacket);
	}

}
