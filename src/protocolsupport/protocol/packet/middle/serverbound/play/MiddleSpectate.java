package protocolsupport.protocol.packet.middle.serverbound.play;

import java.util.UUID;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;

public abstract class MiddleSpectate extends ServerBoundMiddlePacket {

	public MiddleSpectate(ConnectionImpl connection) {
		super(connection);
	}

	protected UUID entityUUID;

	@Override
	public void writeToServer() {
		ServerBoundPacketData spetate = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_SPECTATE);
		MiscSerializer.writeUUID(spetate, entityUUID);
		codec.read(spetate);
	}

}
