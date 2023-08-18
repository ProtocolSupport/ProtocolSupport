package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_16r1__20;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleJigsawUpdate;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV16r1;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV16r2;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV17r2;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV18;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV20;

public class JigsawUpdate extends MiddleJigsawUpdate implements
IServerboundMiddlePacketV16r1,
IServerboundMiddlePacketV16r2,
IServerboundMiddlePacketV17r1,
IServerboundMiddlePacketV17r2,
IServerboundMiddlePacketV18,
IServerboundMiddlePacketV20 {

	public JigsawUpdate(IMiddlePacketInit init) {
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
