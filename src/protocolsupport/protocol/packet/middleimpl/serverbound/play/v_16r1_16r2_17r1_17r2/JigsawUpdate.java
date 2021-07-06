package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_16r1_16r2_17r1_17r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleJigsawUpdate;

public class JigsawUpdate extends MiddleJigsawUpdate {

	public JigsawUpdate(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		PositionCodec.readPosition(clientdata, position);
		name = StringCodec.readVarIntUTF8String(clientdata, Short.MAX_VALUE);
		target = StringCodec.readVarIntUTF8String(clientdata, Short.MAX_VALUE);
		pool = StringCodec.readVarIntUTF8String(clientdata, Short.MAX_VALUE);
		finalState = StringCodec.readVarIntUTF8String(clientdata, Short.MAX_VALUE);
		jointType = StringCodec.readVarIntUTF8String(clientdata, Short.MAX_VALUE);
	}

}
