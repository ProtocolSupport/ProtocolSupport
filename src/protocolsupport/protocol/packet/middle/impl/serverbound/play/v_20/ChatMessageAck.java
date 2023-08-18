package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_20;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleChatMessageAck;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV20;

public class ChatMessageAck extends MiddleChatMessageAck implements
IServerboundMiddlePacketV20 {

	public ChatMessageAck(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		messageCount = VarNumberCodec.readVarInt(clientdata);
	}

}
