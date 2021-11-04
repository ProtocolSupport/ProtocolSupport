package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_15;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleJigsawUpdate;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV15;

//TODO: attempt to implement
public class JigsawUpdate extends MiddleJigsawUpdate implements IServerboundMiddlePacketV15 {

	public JigsawUpdate(IMiddlePacketInit init) {
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
