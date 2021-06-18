package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleSetHealth extends ClientBoundMiddlePacket {

	protected MiddleSetHealth(MiddlePacketInit init) {
		super(init);
	}

	protected float health;
	protected int food;
	protected float saturation;

	@Override
	protected void decode(ByteBuf serverdata) {
		health = serverdata.readFloat();
		food = VarNumberCodec.readVarInt(serverdata);
		saturation = serverdata.readFloat();
	}

}
