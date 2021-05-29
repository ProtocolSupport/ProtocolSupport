package protocolsupport.protocol.packet.middle.clientbound.login;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleSetCompression extends ClientBoundMiddlePacket {

	protected MiddleSetCompression(MiddlePacketInit init) {
		super(init);
	}

	protected int threshold;

	@Override
	protected void decode(ByteBuf serverdata) {
		threshold = VarNumberSerializer.readVarInt(serverdata);
	}

}
