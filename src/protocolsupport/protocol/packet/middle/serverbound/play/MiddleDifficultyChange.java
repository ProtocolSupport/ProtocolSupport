package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.types.Difficulty;

public abstract class MiddleDifficultyChange extends ServerBoundMiddlePacket {

	protected MiddleDifficultyChange(MiddlePacketInit init) {
		super(init);
	}

	protected Difficulty difficulty;

	@Override
	protected void write() {
		//TODO: evaluate if the actual packet should be written
	}

}
