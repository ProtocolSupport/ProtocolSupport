package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.StringSerializer;

public abstract class MiddleTabComplete extends ClientBoundMiddlePacket {

	protected String[] matches;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		matches = ArraySerializer.readVarIntTArray(
			serverdata, String.class,
			(from) -> StringSerializer.readString(from, ProtocolVersion.getLatest(ProtocolType.PC))
		);
	}

}
