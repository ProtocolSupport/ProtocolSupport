package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2__1_10;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleKeepAlive;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class KeepAlive extends MiddleKeepAlive {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		keepAliveId = serializer.readVarInt();
	}

}
