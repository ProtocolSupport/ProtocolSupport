package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11;

import com.google.gson.JsonParser;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUpdateSign;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;

public class UpdateSign extends MiddleUpdateSign {

	private final JsonParser parser = new JsonParser();

	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		position = PositionSerializer.readPosition(clientdata);
		for (int i = 0; i < 4; i++) {
			String rawline = StringSerializer.readString(clientdata, version);
			lines[i] = version.isAfter(ProtocolVersion.MINECRAFT_1_8) ? rawline : parser.parse(rawline).getAsString();
		}
	}

}
