package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_17r1_17r2;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleSetPosition;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r2;

public class SetPosition extends MiddleSetPosition implements
IClientboundMiddlePacketV17r1,
IClientboundMiddlePacketV17r2 {

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
		setpositionPacket.writeBoolean(leaveVehicle);
		io.writeClientbound(setpositionPacket);
	}

}
