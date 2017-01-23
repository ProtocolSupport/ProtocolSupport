package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockDig;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class BlockDig extends MiddleBlockDig {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		status = serializer.readUnsignedByte();
		position = serializer.readLegacyPositionB();
		face = serializer.readUnsignedByte();
	}

}
