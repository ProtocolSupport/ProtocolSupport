package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleTabComplete extends ClientBoundMiddlePacket {

	protected int id;
	//TODO: structure
	protected byte[] data;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		id = VarNumberSerializer.readVarInt(serverdata);
		data = MiscSerializer.readAllBytes(serverdata);
	}

}
