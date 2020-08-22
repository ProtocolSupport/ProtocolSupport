package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleTeleportAccept extends ServerBoundMiddlePacket {

	public MiddleTeleportAccept(MiddlePacketInit init) {
		super(init);
	}

	protected int teleportConfirmId;

	@Override
	protected void writeToServer() {
		codec.read(create(teleportConfirmId));
	}

	public static ServerBoundPacketData create(int teleportId) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_TELEPORT_ACCEPT);
		VarNumberSerializer.writeVarInt(creator, teleportId);
		return creator;
	}

}
