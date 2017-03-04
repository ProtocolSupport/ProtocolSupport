package protocolsupport.protocol.packet.middle.clientbound.login;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleSetCompression extends ClientBoundMiddlePacket {

	protected int threshold;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		threshold = VarNumberSerializer.readVarInt(serverdata);
	}

}
