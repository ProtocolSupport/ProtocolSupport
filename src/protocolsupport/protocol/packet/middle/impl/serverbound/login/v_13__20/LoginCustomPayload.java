package protocolsupport.protocol.packet.middle.impl.serverbound.login.v_13__20;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.BytesCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.serverbound.login.MiddleLoginCustomPayload;
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

public class LoginCustomPayload extends MiddleLoginCustomPayload implements
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

	public LoginCustomPayload(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		id = VarNumberCodec.readVarInt(clientdata);
		if (clientdata.readBoolean()) {
			data = BytesCodec.readAllBytesSlice(clientdata, 1048576);
		} else {
			data = null;
		}
	}

}
