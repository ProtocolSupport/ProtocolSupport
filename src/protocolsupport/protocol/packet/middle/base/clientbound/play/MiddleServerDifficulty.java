package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;
import protocolsupport.protocol.types.Difficulty;

public abstract class MiddleServerDifficulty extends ClientBoundMiddlePacket {

	protected MiddleServerDifficulty(IMiddlePacketInit init) {
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
