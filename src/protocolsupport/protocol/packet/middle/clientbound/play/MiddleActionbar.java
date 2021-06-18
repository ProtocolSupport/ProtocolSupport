package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleActionbar extends ClientBoundMiddlePacket {

	protected MiddleActionbar(MiddlePacketInit init) {
		super(init);
	}

	protected BaseComponent text;

	@Override
	protected void decode(ByteBuf serverdata) {
		text = ChatAPI.fromJSON(StringCodec.readVarIntUTF8String(serverdata), true);
	}

}
