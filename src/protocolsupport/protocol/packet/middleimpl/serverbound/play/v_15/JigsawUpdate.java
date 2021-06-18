package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_15;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleJigsawUpdate;

//TODO: attempt to implement
public class JigsawUpdate extends MiddleJigsawUpdate {

	public JigsawUpdate(MiddlePacketInit init) {
		super(init);
		jointType = "aligned";
	}

	@Override
	protected void write() {
	}

	@Override
	protected void read(ByteBuf clientdata) {
		PositionCodec.readPosition(clientdata, position);
		StringCodec.readVarIntUTF8String(clientdata, Short.MAX_VALUE); //attachment type
		pool = StringCodec.readVarIntUTF8String(clientdata, Short.MAX_VALUE);
		finalState = StringCodec.readVarIntUTF8String(clientdata, Short.MAX_VALUE);
	}

}
