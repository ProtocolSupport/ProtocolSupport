package protocolsupport.protocol.packet.middle.clientbound.login;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public abstract class MiddleLoginSuccess extends ClientBoundMiddlePacket {

	public MiddleLoginSuccess(ConnectionImpl connection) {
		super(connection);
	}

	protected String uuidstring;
	protected String name;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		uuidstring = StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC, 36);
		name = StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC, 16);
	}

}
