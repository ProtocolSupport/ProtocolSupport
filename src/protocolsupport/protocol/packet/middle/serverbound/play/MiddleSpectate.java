package protocolsupport.protocol.packet.middle.serverbound.play;

import java.util.UUID;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.UUIDSerializer;

public abstract class MiddleSpectate extends ServerBoundMiddlePacket {

	protected MiddleSpectate(MiddlePacketInit init) {
		super(init);
	}

	protected UUID entityUUID;

	@Override
	protected void write() {
		ServerBoundPacketData spetate = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_SPECTATE);
		UUIDSerializer.writeUUID2L(spetate, entityUUID);
		codec.writeServerbound(spetate);
	}

}
