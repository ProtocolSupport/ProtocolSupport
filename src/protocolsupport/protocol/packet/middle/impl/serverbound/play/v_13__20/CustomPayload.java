package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_13__20;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.BytesCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleCustomPayload;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV13;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV14r1;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV14r2;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV15;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV16r1;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV16r2;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV17r2;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV18;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV20;

public class CustomPayload extends MiddleCustomPayload implements
IServerboundMiddlePacketV13,
IServerboundMiddlePacketV14r1,
IServerboundMiddlePacketV14r2,
IServerboundMiddlePacketV15,
IServerboundMiddlePacketV16r1,
IServerboundMiddlePacketV16r2,
IServerboundMiddlePacketV17r1,
IServerboundMiddlePacketV17r2,
IServerboundMiddlePacketV18,
IServerboundMiddlePacketV20 {

	public CustomPayload(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		tag = StringCodec.readVarIntUTF8String(clientdata, Short.MAX_VALUE);
		data = BytesCodec.readAllBytesSlice(clientdata);
	}

	@Override
	protected String getServerTag(String tag) {
		return tag;
	}

}
