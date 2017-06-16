package protocolsupport.protocol.packet.middleimpl.serverbound.status.v_7_8_9r1_9r2_10_11_12;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.status.MiddlePing;

public class Ping extends MiddlePing {

	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		pingId = clientdata.readLong();
	}

}
