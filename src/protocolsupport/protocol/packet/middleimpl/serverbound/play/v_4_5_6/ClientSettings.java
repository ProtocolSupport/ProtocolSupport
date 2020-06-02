package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6;

import org.bukkit.inventory.MainHand;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleClientSettings;
import protocolsupport.protocol.serializer.StringSerializer;

public class ClientSettings extends MiddleClientSettings {

	public ClientSettings(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void readClientData(ByteBuf clientdata) {
		locale = StringSerializer.readShortUTF16BEString(clientdata, 16);
		viewDist = clientdata.readByte();
		int chatState = clientdata.readByte();
		chatMode = ChatMode.CONSTANT_LOOKUP.getByOrdinal(chatState & 7);
		chatColors = (chatState & 8) == 8;
		clientdata.readByte();
		clientdata.readBoolean();
		skinFlags = 255;
		mainHand = MainHand.RIGHT;
	}

}
