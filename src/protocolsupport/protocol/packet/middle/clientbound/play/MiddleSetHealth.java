package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleSetHealth extends ClientBoundMiddlePacket {

	public MiddleSetHealth(MiddlePacketInit init) {
		super(init);
	}

	protected float health;
	protected int food;
	protected float saturation;

	@Override
	protected void decode(ByteBuf serverdata) {
		health = serverdata.readFloat();
		food = VarNumberSerializer.readVarInt(serverdata);
		saturation = serverdata.readFloat();
	}

}
