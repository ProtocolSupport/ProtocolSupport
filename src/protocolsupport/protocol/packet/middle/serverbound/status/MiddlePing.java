package protocolsupport.protocol.packet.middle.serverbound.status;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;

public abstract class MiddlePing extends ServerBoundMiddlePacket {

	protected MiddlePing(MiddlePacketInit init) {
		super(init);
	}

	protected long pingId;

	@Override
	protected void write() {
		ServerBoundPacketData ping = ServerBoundPacketData.create(PacketType.SERVERBOUND_STATUS_PING);
		ping.writeLong(pingId);
		codec.writeServerbound(ping);
	}

}
