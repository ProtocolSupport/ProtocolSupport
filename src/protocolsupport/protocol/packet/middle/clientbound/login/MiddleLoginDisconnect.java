package protocolsupport.protocol.packet.middle.clientbound.login;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;

public abstract class MiddleLoginDisconnect extends ClientBoundMiddlePacket {

	public MiddleLoginDisconnect(ConnectionImpl connection) {
		super(connection);
	}

	protected BaseComponent message;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		message = ChatAPI.fromJSON(StringSerializer.readVarIntUTF8String(serverdata));
	}

}
