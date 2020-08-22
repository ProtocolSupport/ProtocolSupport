package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;

public abstract class MiddleKickDisconnect extends ClientBoundMiddlePacket {

	public MiddleKickDisconnect(MiddlePacketInit init) {
		super(init);
	}

	protected BaseComponent message;

	@Override
	protected void readServerData(ByteBuf serverdata) {
		message = ChatAPI.fromJSON(StringSerializer.readVarIntUTF8String(serverdata), true);
	}

}
