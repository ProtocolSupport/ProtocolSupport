package protocolsupport.zmcpe.packetsimpl.serverbound;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddlePositionLook;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class PositionLook extends MiddlePositionLook {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		serializer.readVarInt(); //entity id
		x = serializer.readLFloat();
		y = serializer.readLFloat() - 1.6200000047683716F;
		z = serializer.readLFloat();
		pitch = serializer.readLFloat();
		yaw = serializer.readLFloat();
		serializer.readLFloat(); //head yaw
		serializer.readByte(); //mode
		onGround = serializer.readBoolean();
	}

}
