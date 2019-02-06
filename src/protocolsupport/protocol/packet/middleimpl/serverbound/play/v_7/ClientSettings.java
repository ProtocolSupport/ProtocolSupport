package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_7;

import org.bukkit.inventory.MainHand;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleClientSettings;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;

public class ClientSettings extends MiddleClientSettings {

	public ClientSettings(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		locale = StringSerializer.readString(clientdata, version, 16);
		viewDist = clientdata.readByte();
		chatMode = MiscSerializer.readByteEnum(clientdata, ChatMode.CONSTANT_LOOKUP);
		chatColors = clientdata.readBoolean();
		clientdata.readByte();
		clientdata.readBoolean();
		skinFlags = 255;
		mainHand = MainHand.RIGHT;
	}

}
