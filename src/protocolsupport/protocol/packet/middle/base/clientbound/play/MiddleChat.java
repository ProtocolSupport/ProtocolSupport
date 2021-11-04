package protocolsupport.protocol.packet.middle.base.clientbound.play;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.ChatAPI.MessagePosition;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.UUIDCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;
import protocolsupport.protocol.utils.EnumConstantLookup;

public abstract class MiddleChat extends ClientBoundMiddlePacket {

	protected MiddleChat(IMiddlePacketInit init) {
		super(init);
	}

	protected BaseComponent message;
	protected MessagePosition position;
	protected UUID sender;

	@Override
	protected void decode(ByteBuf serverdata) {
		message = ChatAPI.fromJSON(StringCodec.readVarIntUTF8String(serverdata), true);
		position = MiscDataCodec.readByteEnum(serverdata, EnumConstantLookup.MESSAGE_POSITION);
		sender = UUIDCodec.readUUID2L(serverdata);
	}

}
