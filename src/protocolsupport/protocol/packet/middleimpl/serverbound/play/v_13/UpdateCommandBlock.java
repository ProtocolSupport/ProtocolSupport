package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUpdateCommandBlock;

public class UpdateCommandBlock extends MiddleUpdateCommandBlock {

	public UpdateCommandBlock(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		PositionCodec.readPositionLXYZ(clientdata, position);
		command = StringCodec.readVarIntUTF8String(clientdata, Short.MAX_VALUE);
		mode = MiscDataCodec.readVarIntEnum(clientdata, Mode.CONSTANT_LOOKUP);
		flags = clientdata.readUnsignedByte();
	}

}
