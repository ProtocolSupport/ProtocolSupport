package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.ChatAPI.MessagePosition;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.EnumConstantLookups;

public abstract class MiddleChat extends ClientBoundMiddlePacket {

	public MiddleChat(ConnectionImpl connection) {
		super(connection);
	}

	protected BaseComponent message;
	protected MessagePosition position;

	@Override
	protected void readServerData(ByteBuf serverdata) {
		message = ChatAPI.fromJSON(StringSerializer.readVarIntUTF8String(serverdata), true);
		position = MiscSerializer.readByteEnum(serverdata, EnumConstantLookups.MESSAGE_POSITION);
	}

}
