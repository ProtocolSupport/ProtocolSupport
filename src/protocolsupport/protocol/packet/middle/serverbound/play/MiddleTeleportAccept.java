package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleTeleportAccept extends ServerBoundMiddlePacket {

	public MiddleTeleportAccept(ConnectionImpl connection) {
		super(connection);
	}

	protected int teleportConfirmId;

	@Override
	public void writeToServer() {
		codec.read(create(teleportConfirmId));
	}

	public static ServerBoundPacketData create(int teleportId) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_TELEPORT_ACCEPT);
		VarNumberSerializer.writeVarInt(creator, teleportId);
		return creator;
	}

}
