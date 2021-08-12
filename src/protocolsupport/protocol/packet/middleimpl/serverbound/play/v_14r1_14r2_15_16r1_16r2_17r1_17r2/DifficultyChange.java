package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_14r1_14r2_15_16r1_16r2_17r1_17r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleDifficultyChange;
import protocolsupport.protocol.types.Difficulty;

public class DifficultyChange extends MiddleDifficultyChange {

	public DifficultyChange(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		difficulty = MiscDataCodec.readByteEnum(clientdata, Difficulty.CONSTANT_LOOKUP);
	}

}
