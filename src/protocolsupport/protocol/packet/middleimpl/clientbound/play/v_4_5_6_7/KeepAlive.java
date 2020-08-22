package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleKeepAlive;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class KeepAlive extends MiddleKeepAlive {

	public KeepAlive(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData keepalive = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_KEEP_ALIVE);
		keepalive.writeInt(keepAliveId);
		codec.write(keepalive);
	}

}
