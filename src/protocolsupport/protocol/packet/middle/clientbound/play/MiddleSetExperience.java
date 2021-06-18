package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleSetExperience extends ClientBoundMiddlePacket {

	protected MiddleSetExperience(MiddlePacketInit init) {
		super(init);
	}

	protected float exp;
	protected int level;
	protected int totalExp;

	@Override
	protected void decode(ByteBuf serverdata) {
		exp = serverdata.readFloat();
		level = VarNumberCodec.readVarInt(serverdata);
		totalExp = VarNumberCodec.readVarInt(serverdata);
	}

}
