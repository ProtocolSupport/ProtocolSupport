package protocolsupport.protocol.packet.middle.base.serverbound.status;

import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;

public abstract class MiddleServerInfoRequest extends ServerBoundMiddlePacket {

	protected MiddleServerInfoRequest(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		io.writeServerbound(ServerBoundPacketData.create(ServerBoundPacketType.STATUS_REQUEST));
	}

}
