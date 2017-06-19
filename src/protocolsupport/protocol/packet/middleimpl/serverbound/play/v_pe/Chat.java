package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import java.text.MessageFormat;
import java.util.HashSet;

import org.apache.commons.lang3.Validate;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleChat;
import protocolsupport.protocol.serializer.StringSerializer;

public class Chat extends MiddleChat {

	private static final int validChatType = 1;

	public static final HashSet<Character> cmdCharacters = new HashSet<>();
	static {
		cmdCharacters.add('!');
		cmdCharacters.add('.');
	}

	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		int type = clientdata.readUnsignedByte();
		Validate.isTrue(type == validChatType, MessageFormat.format("Unxepected serverbound chat type, expected {0}, but received {1}", validChatType, type));
		StringSerializer.readString(clientdata, version); //skip sender
		message = StringSerializer.readString(clientdata, version);
		if ((message.length() >= 2) && cmdCharacters.contains(message.charAt(0)) && (message.charAt(1) == '/')) {
			message = message.substring(1);
		}
	}

}