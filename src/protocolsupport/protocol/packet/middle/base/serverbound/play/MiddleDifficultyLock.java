package protocolsupport.protocol.packet.middle.base.serverbound.play;

import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;

public abstract class MiddleDifficultyLock extends ServerBoundMiddlePacket {

	protected MiddleDifficultyLock(IMiddlePacketInit init) {
		super(init);
	}

	protected boolean lock;

	@Override
	protected void write() {
		//TODO: evaluate if the actual packet should be written
	}

}
