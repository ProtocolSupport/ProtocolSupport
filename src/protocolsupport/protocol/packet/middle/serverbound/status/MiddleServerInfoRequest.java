package protocolsupport.protocol.packet.middle.serverbound.status;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;

public abstract class MiddleServerInfoRequest extends ServerBoundMiddlePacket {

	public MiddleServerInfoRequest(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeToServer() {
		codec.read(ServerBoundPacketData.create(PacketType.SERVERBOUND_STATUS_REQUEST));
	}

}
