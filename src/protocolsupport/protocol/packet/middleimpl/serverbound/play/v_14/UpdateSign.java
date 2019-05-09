package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_14;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUpdateSign;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;

public class UpdateSign extends MiddleUpdateSign {

	public UpdateSign(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		PositionSerializer.readPositionTo(clientdata, position);
		for (int i = 0; i < lines.length; i++) {
			String rawline = StringSerializer.readString(clientdata, version);
			lines[i] = version.isAfter(ProtocolVersion.MINECRAFT_1_8) ? rawline : ChatAPI.fromJSON(rawline).toLegacyText(cache.getAttributesCache().getLocale());
		}
	}

}
