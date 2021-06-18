package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;

public abstract class MiddleTeleportAccept extends ServerBoundMiddlePacket {

	protected MiddleTeleportAccept(MiddlePacketInit init) {
		super(init);
	}

	protected int teleportConfirmId;

	@Override
	protected void write() {
		codec.writeServerbound(create(teleportConfirmId));
	}

	public static ServerBoundPacketData create(int teleportId) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_TELEPORT_ACCEPT);
		VarNumberCodec.writeVarInt(creator, teleportId);
		return creator;
	}

}
