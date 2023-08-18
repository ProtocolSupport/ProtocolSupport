package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_20;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ProfileCodec;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleChatSessionUpdate;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV20;

public class ChatSessionUpdate extends MiddleChatSessionUpdate implements
IServerboundMiddlePacketV20 {

	public ChatSessionUpdate(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		session = ProfileCodec.readChatSession(clientdata);
	}

}
