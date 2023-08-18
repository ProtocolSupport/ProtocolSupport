package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;

public abstract class MiddleSystemMessage extends ClientBoundMiddlePacket {

	protected MiddleSystemMessage(IMiddlePacketInit init) {
		super(init);
	}

	protected BaseComponent message;
	protected boolean overlay;

	@Override
	protected void decode(ByteBuf serverdata) {
		message = ChatAPI.fromJSON(StringCodec.readVarIntUTF8String(serverdata), true);
		overlay = serverdata.readBoolean();
	}

}
