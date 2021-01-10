package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.ChatAPI.MessagePosition;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.UUIDSerializer;
import protocolsupport.protocol.utils.EnumConstantLookups;

public abstract class MiddleChat extends ClientBoundMiddlePacket {

	public MiddleChat(MiddlePacketInit init) {
		super(init);
	}

	protected BaseComponent message;
	protected MessagePosition position;
	protected UUID sender;

	@Override
	protected void decode(ByteBuf serverdata) {
		message = ChatAPI.fromJSON(StringSerializer.readVarIntUTF8String(serverdata), true);
		position = MiscSerializer.readByteEnum(serverdata, EnumConstantLookups.MESSAGE_POSITION);
		sender = UUIDSerializer.readUUID2L(serverdata);
	}

}
