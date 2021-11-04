package protocolsupport.protocol.packet.middle.base.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;

public abstract class MiddlePlayerAbilities extends ServerBoundMiddlePacket {

	protected MiddlePlayerAbilities(IMiddlePacketInit init) {
		super(init);
	}

	protected int flags;

	@Override
	protected void write() {
		ServerBoundPacketData abilities = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_ABILITIES);
		abilities.writeByte(flags);
		io.writeServerbound(abilities);
	}

}
