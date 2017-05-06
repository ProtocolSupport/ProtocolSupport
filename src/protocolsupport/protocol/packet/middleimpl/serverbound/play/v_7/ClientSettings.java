package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_7;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleClientSettings;
import protocolsupport.protocol.serializer.StringSerializer;

public class ClientSettings extends MiddleClientSettings {

	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		locale = StringSerializer.readString(clientdata, version, 7);
		viewDist = clientdata.readByte();
		chatMode = clientdata.readByte();
		chatColors = clientdata.readBoolean();
		clientdata.readByte();
		clientdata.readBoolean();
		skinFlags = 255;
		mainHand = 1;
	}

}
