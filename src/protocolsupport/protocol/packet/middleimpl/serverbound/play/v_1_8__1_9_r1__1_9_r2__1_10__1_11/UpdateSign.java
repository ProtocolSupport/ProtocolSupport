package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import io.netty.handler.codec.DecoderException;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUpdateSign;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class UpdateSign extends MiddleUpdateSign {

	private final JSONParser parser = new JSONParser();

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		position = serializer.readPosition();
		for (int i = 0; i < 4; i++) {
			try {
				lines[i] = (String) (serializer.getVersion().isAfter(ProtocolVersion.MINECRAFT_1_8) ? serializer.readString() : parser.parse(serializer.readString()));
			} catch (ParseException e) {
				throw new DecoderException(e);
			}
		}
	}

}
