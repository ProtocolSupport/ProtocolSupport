package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUpdateCommandBlock;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;

public class UpdateCommandBlock extends MiddleUpdateCommandBlock {

	public UpdateCommandBlock(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		PositionSerializer.readLegacyPositionLTo(clientdata, position);
		command = StringSerializer.readVarIntUTF8String(clientdata, Short.MAX_VALUE);
		mode = MiscSerializer.readVarIntEnum(clientdata, Mode.CONSTANT_LOOKUP);
		flags = clientdata.readUnsignedByte();
	}

}
