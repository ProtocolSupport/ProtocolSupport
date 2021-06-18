package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleResourcePack extends ClientBoundMiddlePacket {

	protected MiddleResourcePack(MiddlePacketInit init) {
		super(init);
	}

	protected String url;
	protected String hash;
	protected boolean forced;
	protected BaseComponent forcedText;

	@Override
	protected void decode(ByteBuf serverdata) {
		url = StringCodec.readVarIntUTF8String(serverdata);
		hash = StringCodec.readVarIntUTF8String(serverdata);
		forced = serverdata.readBoolean();
		forcedText = ChatAPI.fromJSON(StringCodec.readVarIntUTF8String(serverdata), true);
	}

}
