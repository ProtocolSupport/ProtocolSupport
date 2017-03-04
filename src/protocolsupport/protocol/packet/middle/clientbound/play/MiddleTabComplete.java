package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleTabComplete extends ClientBoundMiddlePacket {

	protected String[] matches;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		matches = new String[VarNumberSerializer.readVarInt(serverdata)];
		for (int i = 0; i < matches.length; i++) {
			matches[i] = StringSerializer.readString(serverdata, ProtocolVersion.getLatest());
		}
	}

}
