package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUpdateCommandBlock;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;

public class UpdateCommandBlock extends MiddleUpdateCommandBlock {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		PositionSerializer.readPositionTo(clientdata, position);
		command = StringSerializer.readString(clientdata, connection.getVersion());
		mode = MiscSerializer.readVarIntEnum(clientdata, Mode.CONSTANT_LOOKUP);
		flags = clientdata.readUnsignedByte();
	}

}
