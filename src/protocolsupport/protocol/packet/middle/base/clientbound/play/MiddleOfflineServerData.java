package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.codec.OptionalCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;

public abstract class MiddleOfflineServerData extends ClientBoundMiddlePacket {

	protected MiddleOfflineServerData(IMiddlePacketInit init) {
		super(init);
	}

	protected BaseComponent motd;
	protected String icon;
	protected boolean secureChat;

	@Override
	protected void decode(ByteBuf serverdata) {
		motd = ChatAPI.fromJSON(StringCodec.readVarIntUTF8String(serverdata));
		icon = OptionalCodec.readOptionalVarIntUTF8String(serverdata);
		secureChat = serverdata.readBoolean();
	}

}
