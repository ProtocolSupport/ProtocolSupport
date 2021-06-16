package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;

public abstract class MiddlePlayerAbilities extends ServerBoundMiddlePacket {

	protected MiddlePlayerAbilities(MiddlePacketInit init) {
		super(init);
	}

	protected int flags;

	@Override
	protected void write() {
		ServerBoundPacketData abilities = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_ABILITIES);
		abilities.writeByte(flags);
		codec.writeServerbound(abilities);
	}

}
