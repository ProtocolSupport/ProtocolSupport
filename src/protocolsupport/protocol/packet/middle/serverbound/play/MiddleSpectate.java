package protocolsupport.protocol.packet.middle.serverbound.play;

import java.util.UUID;

import protocolsupport.protocol.codec.UUIDCodec;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;

public abstract class MiddleSpectate extends ServerBoundMiddlePacket {

	protected MiddleSpectate(MiddlePacketInit init) {
		super(init);
	}

	protected UUID entityUUID;

	@Override
	protected void write() {
		ServerBoundPacketData spetate = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_SPECTATE);
		UUIDCodec.writeUUID2L(spetate, entityUUID);
		codec.writeServerbound(spetate);
	}

}
