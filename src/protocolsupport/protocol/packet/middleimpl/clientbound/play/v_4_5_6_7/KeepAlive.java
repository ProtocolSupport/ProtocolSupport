package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleKeepAlive;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class KeepAlive extends MiddleKeepAlive {

	public KeepAlive(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData keepalive = ClientBoundPacketData.create(ClientBoundPacketType.CLIENTBOUND_PLAY_KEEP_ALIVE);
		keepalive.writeInt(keepAliveId);
		codec.writeClientbound(keepalive);
	}

}
