package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;

public abstract class MiddleTitleSubText extends ClientBoundMiddlePacket {

	protected MiddleTitleSubText(IMiddlePacketInit init) {
		super(init);
	}

	protected BaseComponent text;

	@Override
	protected void decode(ByteBuf serverdata) {
		text = ChatAPI.fromJSON(StringCodec.readVarIntUTF8String(serverdata), true);
	}

}
