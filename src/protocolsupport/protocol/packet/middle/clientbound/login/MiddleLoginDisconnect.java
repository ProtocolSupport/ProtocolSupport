package protocolsupport.protocol.packet.middle.clientbound.login;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public abstract class MiddleLoginDisconnect extends ClientBoundMiddlePacket {

	protected BaseComponent message;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		message = ChatAPI.fromJSON(StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC));
	}

}
