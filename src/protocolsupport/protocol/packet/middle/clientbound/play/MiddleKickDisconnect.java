package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleKickDisconnect extends ClientBoundMiddlePacket {

	protected MiddleKickDisconnect(MiddlePacketInit init) {
		super(init);
	}

	protected BaseComponent message;

	@Override
	protected void decode(ByteBuf serverdata) {
		message = ChatAPI.fromJSON(StringCodec.readVarIntUTF8String(serverdata), true);
	}

}
