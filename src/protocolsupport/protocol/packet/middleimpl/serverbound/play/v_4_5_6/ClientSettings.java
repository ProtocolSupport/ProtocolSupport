package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6;

import org.bukkit.inventory.MainHand;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleClientSettings;

public class ClientSettings extends MiddleClientSettings {

	public ClientSettings(MiddlePacketInit init) {
		super(init);
		mainHand = MainHand.RIGHT;
		disableTextFilter = true;
	}

	@Override
	protected void read(ByteBuf clientdata) {
		locale = StringCodec.readShortUTF16BEString(clientdata, 16);
		viewDist = clientdata.readByte();
		int chatState = clientdata.readByte();
		chatMode = ChatMode.CONSTANT_LOOKUP.getByOrdinal(chatState & 7);
		chatColors = (chatState & 8) == 8;
		clientdata.readByte();
		clientdata.readBoolean();
		skinFlags = 255;
	}

}
