package protocolsupport.protocol.packet.middle.base.serverbound.play;

import protocolsupport.protocol.codec.ProfileCodec;
import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;
import protocolsupport.protocol.types.ChatSession;

public abstract class MiddleChatSessionUpdate extends ServerBoundMiddlePacket {

	protected MiddleChatSessionUpdate(IMiddlePacketInit init) {
		super(init);
	}

	protected ChatSession session;

	@Override
	protected void write() {
		ServerBoundPacketData chatsessionupdatePacket = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_CHAT_SESSION);
		ProfileCodec.writeChatSession(chatsessionupdatePacket, session);
		io.writeServerbound(chatsessionupdatePacket);
	}

}
