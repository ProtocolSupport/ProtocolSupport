package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public abstract class MiddleTabComplete extends ClientBoundMiddlePacket {

	protected String[] matches;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		matches = ArraySerializer.readVarIntTArray(
			serverdata, String.class,
			(from) -> StringSerializer.readString(from, ProtocolVersionsHelper.LATEST_PC)
		);
	}

	@Override
	public boolean postFromServerRead() {
		return matches.length > 0;
	}

}
