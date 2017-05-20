package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;

public abstract class MiddleAdvancementProgress extends ClientBoundMiddlePacket {

	protected String identifier;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		if (serverdata.readBoolean()) {
			identifier = StringSerializer.readString(serverdata, ProtocolVersion.getLatest(ProtocolType.PC));
		} else {
			identifier = null;
		}
	}

}
