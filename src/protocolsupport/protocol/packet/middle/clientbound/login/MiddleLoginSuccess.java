package protocolsupport.protocol.packet.middle.clientbound.login;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;

public abstract class MiddleLoginSuccess extends ClientBoundMiddlePacket {

	protected String uuidstring;
	protected String name;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		uuidstring = StringSerializer.readString(serverdata, ProtocolVersion.getLatest(ProtocolType.PC), 36);
		name = StringSerializer.readString(serverdata, ProtocolVersion.getLatest(ProtocolType.PC), 16);
	}

}
