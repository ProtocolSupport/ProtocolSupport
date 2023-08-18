package protocolsupport.protocol.packet.middle.base.serverbound.play;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;

public abstract class MiddleChatMessageAck extends ServerBoundMiddlePacket {

	protected MiddleChatMessageAck(IMiddlePacketInit init) {
		super(init);
	}

	protected int messageCount;

	@Override
	protected void write() {
		ServerBoundPacketData messageack = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_CHAT_MESSAGE_ACK);
		VarNumberCodec.writeVarInt(messageack, messageCount);
		io.writeServerbound(messageack);
	}

}
