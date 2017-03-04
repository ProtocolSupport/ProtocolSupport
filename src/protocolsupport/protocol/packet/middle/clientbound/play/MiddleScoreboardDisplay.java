package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;

public abstract class MiddleScoreboardDisplay extends ClientBoundMiddlePacket {

	protected int position;
	protected String name;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		position = serverdata.readUnsignedByte();
		name = StringSerializer.readString(serverdata, ProtocolVersion.getLatest(), 16);
	}

}
