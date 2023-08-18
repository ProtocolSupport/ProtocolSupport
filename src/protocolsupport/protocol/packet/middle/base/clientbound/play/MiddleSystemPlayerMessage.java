package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.codec.OptionalCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;

public abstract class MiddleSystemPlayerMessage extends ClientBoundMiddlePacket {

	protected MiddleSystemPlayerMessage(IMiddlePacketInit init) {
		super(init);
	}

	protected BaseComponent message;
	protected int chatType;
	protected BaseComponent senderDisplayName;
	protected BaseComponent targetDisplayName;

	@Override
	protected void decode(ByteBuf serverdata) {
		message = ChatAPI.fromJSON(StringCodec.readVarIntUTF8String(serverdata), true);
		chatType = VarNumberCodec.readVarInt(serverdata);
		senderDisplayName = ChatAPI.fromJSON(StringCodec.readVarIntUTF8String(serverdata), true);
		targetDisplayName = OptionalCodec.readOptional(serverdata, unsignedMessageData -> ChatAPI.fromJSON(StringCodec.readVarIntUTF8String(unsignedMessageData), true));
	}

}
