package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;

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
		StringSerializer.writeVarIntUTF8String(creator, message);
		return creator;
	}

}
