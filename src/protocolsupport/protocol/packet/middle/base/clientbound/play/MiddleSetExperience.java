package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;

public abstract class MiddleSetExperience extends ClientBoundMiddlePacket {

	protected MiddleSetExperience(IMiddlePacketInit init) {
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
