package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_7;

import org.bukkit.inventory.MainHand;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleClientSettings;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;

public class ClientSettings extends MiddleClientSettings {

	public ClientSettings(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void readClientData(ByteBuf clientdata) {
		locale = StringSerializer.readVarIntUTF8String(clientdata, 16);
		viewDist = clientdata.readByte();
		chatMode = MiscSerializer.readByteEnum(clientdata, ChatMode.CONSTANT_LOOKUP);
		chatColors = clientdata.readBoolean();
		clientdata.readByte();
		clientdata.readBoolean();
		skinFlags = 255;
		mainHand = MainHand.RIGHT;
	}

}
