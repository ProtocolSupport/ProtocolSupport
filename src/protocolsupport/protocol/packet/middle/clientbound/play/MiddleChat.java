package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.ChatAPI.MessagePosition;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.EnumConstantLookups;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public abstract class MiddleChat extends ClientBoundMiddlePacket {

	protected BaseComponent message;
	protected MessagePosition position;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		message = ChatAPI.fromJSON(StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC));
		position = MiscSerializer.readByteEnum(serverdata, EnumConstantLookups.MESSAGE_POSITION);
	}

	@Override
	public boolean postFromServerRead() {
		if (
			(position == MessagePosition.HOTBAR) &&
			(connection.getVersion().getProtocolType() == ProtocolType.PC) &&
			connection.getVersion().isBefore(ProtocolVersion.MINECRAFT_1_8)
		) {
			return false;
		}
		return true;
	}

}
