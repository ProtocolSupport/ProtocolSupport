package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.ChatAPI.MessagePosition;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public abstract class MiddleChat extends ClientBoundMiddlePacket {

	protected BaseComponent message;
	protected MessagePosition position;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		message = ChatAPI.fromJSON(StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC));
		position = MessagePosition.values()[serverdata.readByte()];
	}

	@Override
	public boolean postFromServerRead() {
		return connection.getVersion().isAfterOrEq(ProtocolVersion.MINECRAFT_1_8) || position != MessagePosition.HOTBAR;
	}

}
