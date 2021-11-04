package protocolsupport.protocol.packet.middle.base.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;

public abstract class MiddleGround extends ServerBoundMiddlePacket {

	protected MiddleGround(IMiddlePacketInit init) {
		super(init);
	}

	protected boolean onGround;

	@Override
	protected void write() {
		ServerBoundPacketData groundPacket = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_GROUND);
		groundPacket.writeBoolean(onGround);
		io.writeServerbound(groundPacket);
	}

}
