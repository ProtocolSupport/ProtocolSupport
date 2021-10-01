package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;

public abstract class MiddleGround extends ServerBoundMiddlePacket {

	protected MiddleGround(MiddlePacketInit init) {
		super(init);
	}

	protected boolean onGround;

	@Override
	protected void write() {
		ServerBoundPacketData groundPacket = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_GROUND);
		groundPacket.writeBoolean(onGround);
		codec.writeServerbound(groundPacket);
	}

}
