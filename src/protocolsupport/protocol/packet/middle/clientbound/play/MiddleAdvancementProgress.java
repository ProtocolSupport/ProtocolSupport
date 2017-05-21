package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.utils.ProtocolVersionsHelper;

public abstract class MiddleAdvancementProgress extends ClientBoundMiddlePacket {

	protected String identifier;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		if (serverdata.readBoolean()) {
			identifier = StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC);
		} else {
			identifier = null;
		}
	}

}
