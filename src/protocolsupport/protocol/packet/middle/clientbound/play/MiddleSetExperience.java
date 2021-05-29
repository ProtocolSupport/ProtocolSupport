package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.VarNumberSerializer;

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
		level = VarNumberSerializer.readVarInt(serverdata);
		totalExp = VarNumberSerializer.readVarInt(serverdata);
	}

}
