package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_7__20;

import java.util.BitSet;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleChatCommand;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleChatCommand.ArgumentSignature;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleChatMessage;
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
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV7;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV9r1;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV9r2;

public class Chat extends ServerBoundMiddlePacket implements
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
IServerboundMiddlePacketV18 {

	public Chat(IMiddlePacketInit init) {
		super(init);
	}

	protected String message;

	@Override
	protected void read(ByteBuf clientdata) {
		message = StringCodec.readVarIntUTF8String(clientdata, Short.MAX_VALUE);
	}

	@Override
	protected void write() {
		if (message.startsWith("/")) {
			io.writeServerbound(MiddleChatCommand.create(message.substring(1), 0, 0, new ArgumentSignature[0], 0, new BitSet()));
		} else {
			io.writeServerbound(MiddleChatMessage.create(message, 0, 0, null, 0, new BitSet()));
		}
	}

}
