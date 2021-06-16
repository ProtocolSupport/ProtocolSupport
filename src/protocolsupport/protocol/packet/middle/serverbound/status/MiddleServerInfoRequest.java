package protocolsupport.protocol.packet.middle.serverbound.status;

import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;

public abstract class MiddleServerInfoRequest extends ServerBoundMiddlePacket {

	protected MiddleServerInfoRequest(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		codec.writeServerbound(ServerBoundPacketData.create(ServerBoundPacketType.SERVERBOUND_STATUS_REQUEST));
	}

}
