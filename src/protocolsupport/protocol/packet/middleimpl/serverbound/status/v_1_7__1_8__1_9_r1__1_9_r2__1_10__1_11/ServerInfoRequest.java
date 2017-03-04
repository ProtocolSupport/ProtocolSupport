package protocolsupport.protocol.packet.middleimpl.serverbound.status.v_1_7__1_8__1_9_r1__1_9_r2__1_10__1_11;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.status.MiddleServerInfoRequest;

public class ServerInfoRequest extends MiddleServerInfoRequest {

	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
	}

}
