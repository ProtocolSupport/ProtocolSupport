package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.codec.OptionalCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;

public abstract class MiddleResourcePack extends ClientBoundMiddlePacket {

	protected MiddleResourcePack(IMiddlePacketInit init) {
		super(init);
	}

	protected String url;
	protected String hash;
	protected boolean forced;
	protected BaseComponent message;

	@Override
	protected void decode(ByteBuf serverdata) {
		url = StringCodec.readVarIntUTF8String(serverdata);
		hash = StringCodec.readVarIntUTF8String(serverdata);
		forced = serverdata.readBoolean();
		message = OptionalCodec.readOptional(serverdata, messageData -> ChatAPI.fromJSON(StringCodec.readVarIntUTF8String(messageData), true));
	}

}
