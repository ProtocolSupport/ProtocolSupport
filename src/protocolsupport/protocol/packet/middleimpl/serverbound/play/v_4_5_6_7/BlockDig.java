package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockDig;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;

public class BlockDig extends MiddleBlockDig {

	public BlockDig(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		status = MiscSerializer.readByteEnum(clientdata, Action.CONSTANT_LOOKUP);
		PositionSerializer.readLegacyPositionBTo(clientdata, position);
		face = clientdata.readUnsignedByte();
	}

}
