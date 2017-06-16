package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUpdateSign;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;

public class UpdateSign extends MiddleUpdateSign {

	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		position = PositionSerializer.readPosition(clientdata);
		for (int i = 0; i < 4; i++) {
			String rawline = StringSerializer.readString(clientdata, version);
			lines[i] = version.isAfter(ProtocolVersion.MINECRAFT_1_8) ? rawline : ChatAPI.fromJSON(rawline).toLegacyText();
		}
	}

}
