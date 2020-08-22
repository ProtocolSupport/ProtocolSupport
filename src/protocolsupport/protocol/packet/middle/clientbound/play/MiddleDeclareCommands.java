package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;

public abstract class MiddleDeclareCommands extends ClientBoundMiddlePacket {

	public MiddleDeclareCommands(MiddlePacketInit init) {
		super(init);
	}

	//TODO: structure
	protected ByteBuf data;

	@Override
	protected void readServerData(ByteBuf serverdata) {
		data = MiscSerializer.readAllBytesSlice(serverdata);
	}

}
