package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_18;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleClientSettings;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV18;
import protocolsupport.protocol.utils.EnumConstantLookup;

public class ClientSettings extends MiddleClientSettings implements IServerboundMiddlePacketV18 {

	public ClientSettings(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		locale = StringCodec.readVarIntUTF8String(clientdata, 16);
		viewDist = clientdata.readByte();
		chatMode = MiscDataCodec.readVarIntEnum(clientdata, ChatMode.CONSTANT_LOOKUP);
		chatColors = clientdata.readBoolean();
		skinFlags = clientdata.readUnsignedByte();
		mainHand = MiscDataCodec.readVarIntEnum(clientdata, EnumConstantLookup.MAIN_HAND);
		disableTextFilter = clientdata.readBoolean();
		list = clientdata.readBoolean();
	}

}
