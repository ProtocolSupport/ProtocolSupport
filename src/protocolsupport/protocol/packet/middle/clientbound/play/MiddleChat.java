package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.ChatAPI.MessagePosition;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.EnumConstantLookups;

public abstract class MiddleChat extends ClientBoundMiddlePacket {

	public MiddleChat(ConnectionImpl connection) {
		super(connection);
	}

	protected BaseComponent message;
	protected MessagePosition position;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		message = ChatAPI.fromJSON(StringSerializer.readVarIntUTF8String(serverdata));
		position = MiscSerializer.readByteEnum(serverdata, EnumConstantLookups.MESSAGE_POSITION);
	}

	@Override
	public boolean postFromServerRead() {
		if (
			(position == MessagePosition.HOTBAR) &&
			(version.getProtocolType() == ProtocolType.PC) &&
			version.isBefore(ProtocolVersion.MINECRAFT_1_8)
		) {
			return false;
		}
		return true;
	}

}
