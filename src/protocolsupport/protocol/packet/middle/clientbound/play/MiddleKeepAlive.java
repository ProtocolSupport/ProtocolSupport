package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleKeepAlive extends ClientBoundMiddlePacket {

	protected int keepAliveId;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		keepAliveId = VarNumberSerializer.readVarInt(serverdata);
	}

}
