package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.types.Difficulty;

public abstract class MiddleServerDifficulty extends ClientBoundMiddlePacket {

	protected MiddleServerDifficulty(MiddlePacketInit init) {
		super(init);
	}

	protected Difficulty difficulty;
	protected boolean locked;

	@Override
	protected void decode(ByteBuf serverdata) {
		difficulty = MiscDataCodec.readByteEnum(serverdata, Difficulty.CONSTANT_LOOKUP);
		locked = serverdata.readBoolean();
	}

}
