package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10_11_12r1_12r2_13_14;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleClientSettings;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.EnumConstantLookups;

public class ClientSettings extends MiddleClientSettings {

	public ClientSettings(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		locale = StringSerializer.readString(clientdata, version, 16);
		viewDist = clientdata.readByte();
		chatMode = MiscSerializer.readVarIntEnum(clientdata, ChatMode.CONSTANT_LOOKUP);
		chatColors = clientdata.readBoolean();
		skinFlags = clientdata.readUnsignedByte();
		mainHand = MiscSerializer.readVarIntEnum(clientdata, EnumConstantLookups.MAIN_HAND);
	}

}
