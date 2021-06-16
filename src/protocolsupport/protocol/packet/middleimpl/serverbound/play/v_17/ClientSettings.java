package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_17;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleClientSettings;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.EnumConstantLookup;

public class ClientSettings extends MiddleClientSettings {

	public ClientSettings(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		locale = StringSerializer.readVarIntUTF8String(clientdata, 16);
		viewDist = clientdata.readByte();
		chatMode = MiscSerializer.readVarIntEnum(clientdata, ChatMode.CONSTANT_LOOKUP);
		chatColors = clientdata.readBoolean();
		skinFlags = clientdata.readUnsignedByte();
		mainHand = MiscSerializer.readVarIntEnum(clientdata, EnumConstantLookup.MAIN_HAND);
		disableTextFilter = clientdata.readBoolean();
	}

}
