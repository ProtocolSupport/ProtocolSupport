package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;

public abstract class MiddleChat extends ServerBoundMiddlePacket {

	protected MiddleChat(MiddlePacketInit init) {
		super(init);
	}

	protected String message;

	@Override
	protected void write() {
		codec.writeServerbound(create(message));
	}

	public static ServerBoundPacketData create(String message) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_CHAT);
		StringCodec.writeVarIntUTF8String(creator, message);
		return creator;
	}

}
