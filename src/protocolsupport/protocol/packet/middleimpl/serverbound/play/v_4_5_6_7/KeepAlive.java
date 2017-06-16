package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleKeepAlive;

public class KeepAlive extends MiddleKeepAlive {

	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		keepAliveId = clientdata.readInt();
	}

}
