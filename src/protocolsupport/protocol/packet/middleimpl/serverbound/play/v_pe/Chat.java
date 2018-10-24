package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import java.text.MessageFormat;

import org.apache.commons.lang3.Validate;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleChat;
import protocolsupport.protocol.serializer.StringSerializer;

public class Chat extends MiddleChat {

	public Chat(ConnectionImpl connection) {
		super(connection);
	}

	private static final int validChatType = 1;

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		ProtocolVersion version = connection.getVersion();
		int type = clientdata.readUnsignedByte();
		Validate.isTrue(type == validChatType, MessageFormat.format("Unexpected serverbound chat type, expected {0}, but received {1}", validChatType, type));
		clientdata.readBoolean(); //needs translation
		StringSerializer.readString(clientdata, version); //skip sender
		message = StringSerializer.readString(clientdata, version);
		StringSerializer.readString(clientdata, version); //skip Xbox user ID
		StringSerializer.readString(clientdata, version); //skip Platform chat ID
	}

}