package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUpdateSign;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;

public class UpdateSign extends MiddleUpdateSign {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		PositionSerializer.readLegacyPositionSTo(clientdata, position);
		ProtocolVersion version = connection.getVersion();
		for (int i = 0; i < lines.length; i++) {
			lines[i] = StringSerializer.readString(clientdata, version, 15);
		}
	}

}
