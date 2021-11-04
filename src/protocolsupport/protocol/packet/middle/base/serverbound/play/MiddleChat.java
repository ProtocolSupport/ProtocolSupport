package protocolsupport.protocol.packet.middle.base.serverbound.play;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;

public abstract class MiddleChat extends ServerBoundMiddlePacket {

	protected MiddleChat(IMiddlePacketInit init) {
		super(init);
	}

	protected String message;

	@Override
	protected void write() {
		io.writeServerbound(create(message));
	}

	public static ServerBoundPacketData create(String message) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_CHAT);
		StringCodec.writeVarIntUTF8String(creator, message);
		return creator;
	}

}
