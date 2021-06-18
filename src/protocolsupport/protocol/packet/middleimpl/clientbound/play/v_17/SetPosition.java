package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_17;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSetPosition;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class SetPosition extends MiddleSetPosition {

	public SetPosition(MiddlePacketInit init) {
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
		codec.writeClientbound(setpositionPacket);
	}

}
