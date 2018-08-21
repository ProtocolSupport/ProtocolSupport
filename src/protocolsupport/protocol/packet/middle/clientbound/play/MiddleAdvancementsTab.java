package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public abstract class MiddleAdvancementsTab extends ClientBoundMiddlePacket {

	public MiddleAdvancementsTab(ConnectionImpl connection) {
		super(connection);
	}

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
