package protocolsupport.protocol.packet.mcpe.packet.mcpe.clientbound;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.PEPacketIDs;

public class ChunkRadiusResponse implements ClientboundPEPacket {

	protected int radius;

	public ChunkRadiusResponse(int radius) {
		this.radius = radius;
	}

	@Override
	public int getId() {
		return PEPacketIDs.CHUNK_RADIUS_RESPONSE;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		buf.writeInt(radius);
		return this;
	}

}
