package protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;

public class PongPacket implements ClientboundPEPacket {

	protected long pingId;

	public PongPacket(long pingId) {
		this.pingId = pingId;
	}

	@Override
	public int getId() {
		return PEPacketIDs.PONG;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		buf.writeLong(pingId);
		return this;
	}

}
