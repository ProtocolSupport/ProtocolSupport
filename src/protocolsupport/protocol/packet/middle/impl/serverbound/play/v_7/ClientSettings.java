package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_7;

import org.bukkit.inventory.MainHand;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleClientSettings;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV7;

public class ClientSettings extends MiddleClientSettings implements IServerboundMiddlePacketV7 {

	public ClientSettings(IMiddlePacketInit init) {
		super(init);
		mainHand = MainHand.RIGHT;
		disableTextFilter = true;
		list = true;
	}

	@Override
	protected void read(ByteBuf clientdata) {
		locale = StringCodec.readVarIntUTF8String(clientdata, 16);
		viewDist = clientdata.readByte();
		chatMode = MiscDataCodec.readByteEnum(clientdata, ChatMode.CONSTANT_LOOKUP);
		chatColors = clientdata.readBoolean();
		clientdata.readByte();
		clientdata.readBoolean();
		skinFlags = 255;
	}

}
