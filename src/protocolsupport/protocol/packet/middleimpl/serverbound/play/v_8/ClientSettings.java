package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8;

import org.bukkit.inventory.MainHand;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleClientSettings;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;

public class ClientSettings extends MiddleClientSettings {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		locale = StringSerializer.readString(clientdata, connection.getVersion(), 16);
		viewDist = clientdata.readByte();
		chatMode = MiscSerializer.readByteEnum(clientdata, ChatMode.CONSTANT_LOOKUP);
		chatColors = clientdata.readBoolean();
		skinFlags = clientdata.readUnsignedByte();
		mainHand = MainHand.RIGHT;
	}

}
