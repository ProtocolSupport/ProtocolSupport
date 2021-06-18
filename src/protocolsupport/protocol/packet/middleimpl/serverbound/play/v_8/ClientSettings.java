package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8;

import org.bukkit.inventory.MainHand;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleClientSettings;

public class ClientSettings extends MiddleClientSettings {

	public ClientSettings(MiddlePacketInit init) {
		super(init);
		disableTextFilter = true;
	}

	@Override
	protected void read(ByteBuf clientdata) {
		locale = StringCodec.readVarIntUTF8String(clientdata, 16);
		viewDist = clientdata.readByte();
		chatMode = MiscDataCodec.readByteEnum(clientdata, ChatMode.CONSTANT_LOOKUP);
		chatColors = clientdata.readBoolean();
		skinFlags = clientdata.readUnsignedByte();
		mainHand = MainHand.RIGHT;
	}

}
