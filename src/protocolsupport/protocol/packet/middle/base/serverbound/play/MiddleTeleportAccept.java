package protocolsupport.protocol.packet.middle.base.serverbound.play;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;

public abstract class MiddleTeleportAccept extends ServerBoundMiddlePacket {

	protected MiddleTeleportAccept(IMiddlePacketInit init) {
		super(init);
	}

	protected int teleportConfirmId;

	@Override
	protected void write() {
		io.writeServerbound(create(teleportConfirmId));
	}

	public static ServerBoundPacketData create(int teleportId) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_TELEPORT_ACCEPT);
		VarNumberCodec.writeVarInt(creator, teleportId);
		return creator;
	}

}
