package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__6;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleChatMessage;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV6;

public class Chat extends MiddleChatMessage implements
IServerboundMiddlePacketV4,
IServerboundMiddlePacketV5,
IServerboundMiddlePacketV6 {

	public Chat(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		message = StringCodec.readShortUTF16BEString(clientdata, Short.MAX_VALUE);
	}

}
