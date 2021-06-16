package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;

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
		url = StringSerializer.readVarIntUTF8String(serverdata);
		hash = StringSerializer.readVarIntUTF8String(serverdata);
		forced = serverdata.readBoolean();
		forcedText = ChatAPI.fromJSON(StringSerializer.readVarIntUTF8String(serverdata), true);
	}

}
