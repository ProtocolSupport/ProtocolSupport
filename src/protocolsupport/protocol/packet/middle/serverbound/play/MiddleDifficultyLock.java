package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;

public abstract class MiddleDifficultyLock extends ServerBoundMiddlePacket {

	protected MiddleDifficultyLock(MiddlePacketInit init) {
		super(init);
	}

	protected boolean lock;

	@Override
	protected void write() {
		//TODO: evaluate if the actual packet should be written
	}

}
