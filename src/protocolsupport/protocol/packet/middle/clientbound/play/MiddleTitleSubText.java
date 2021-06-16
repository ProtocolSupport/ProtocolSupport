package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;

public abstract class MiddleTitleSubText extends ClientBoundMiddlePacket {

	protected MiddleTitleSubText(MiddlePacketInit init) {
		super(init);
	}

	protected BaseComponent text;

	@Override
	protected void decode(ByteBuf serverdata) {
		text = ChatAPI.fromJSON(StringSerializer.readVarIntUTF8String(serverdata), true);
	}

}
