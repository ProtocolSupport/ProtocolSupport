package protocolsupport.protocol.packet.middle.base.serverbound.play;

import java.util.UUID;

import protocolsupport.protocol.codec.UUIDCodec;
import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;

public abstract class MiddleSpectate extends ServerBoundMiddlePacket {

	protected MiddleSpectate(IMiddlePacketInit init) {
		super(init);
	}

	protected UUID entityUUID;

	@Override
	protected void write() {
		ServerBoundPacketData spetate = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_SPECTATE);
		UUIDCodec.writeUUID(spetate, entityUUID);
		io.writeServerbound(spetate);
	}

}
