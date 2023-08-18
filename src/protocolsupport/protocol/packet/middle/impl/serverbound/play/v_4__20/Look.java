package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__20;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleLook;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV10;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV11;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV12r1;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV12r2;
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
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV7;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV9r1;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV9r2;

public class Look extends MiddleLook implements
IServerboundMiddlePacketV4,
IServerboundMiddlePacketV5,
IServerboundMiddlePacketV6,
IServerboundMiddlePacketV7,
IServerboundMiddlePacketV8,
IServerboundMiddlePacketV9r1,
IServerboundMiddlePacketV9r2,
IServerboundMiddlePacketV10,
IServerboundMiddlePacketV11,
IServerboundMiddlePacketV12r1,
IServerboundMiddlePacketV12r2,
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

	public Look(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		yaw = clientdata.readFloat();
		pitch = clientdata.readFloat();
		onGround = clientdata.readBoolean();
	}

}
