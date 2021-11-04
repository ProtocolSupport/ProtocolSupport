package protocolsupport.protocol.packet.middle.base.serverbound.play;

import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;
import protocolsupport.protocol.types.Difficulty;

public abstract class MiddleDifficultyChange extends ServerBoundMiddlePacket {

	protected MiddleDifficultyChange(IMiddlePacketInit init) {
		super(init);
	}

	protected Difficulty difficulty;

	@Override
	protected void write() {
		//TODO: evaluate if the actual packet should be written
	}

}
