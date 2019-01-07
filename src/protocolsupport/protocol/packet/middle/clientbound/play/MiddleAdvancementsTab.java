package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;

public abstract class MiddleAdvancementsTab extends ClientBoundMiddlePacket {

	public MiddleAdvancementsTab(ConnectionImpl connection) {
		super(connection);
	}

	protected String identifier;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		if (serverdata.readBoolean()) {
			identifier = StringSerializer.readVarIntUTF8String(serverdata);
		} else {
			identifier = null;
		}
	}

}
