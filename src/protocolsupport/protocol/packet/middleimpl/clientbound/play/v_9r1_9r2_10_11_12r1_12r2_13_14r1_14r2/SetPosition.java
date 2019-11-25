package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSetPosition;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class SetPosition extends MiddleSetPosition {

	public SetPosition(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData setposition = codec.allocClientBoundPacketData(PacketType.CLIENTBOUND_PLAY_POSITION);
		setposition.writeDouble(xOrig);
		setposition.writeDouble(yOrig);
		setposition.writeDouble(zOrig);
		setposition.writeFloat(yawOrig);
		setposition.writeFloat(pitchOrig);
		setposition.writeByte(flags);
		VarNumberSerializer.writeVarInt(setposition, teleportConfirmId);
		codec.write(setposition);
	}

}
