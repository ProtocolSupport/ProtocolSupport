package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8;

import java.io.IOException;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUpdateSign;
import protocolsupport.protocol.serializer.PacketDataSerializer;

public class UpdateSign extends MiddleUpdateSign {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) throws IOException {
		position = serializer.readPosition();
		for (int i = 0; i < 4; i++) {
			try {
				lines[i] = (String) new JSONParser().parse(serializer.readString(Short.MAX_VALUE));
			} catch (ParseException e) {
				throw new IOException(e);
			}
		}
	}

}
