package protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;

public class RespawnPacket implements ClientboundPEPacket {

	protected float x;
	protected float y;
	protected float z;

	@Override
	public int getId() {
		return PEPacketIDs.RESPAWN_PACKET;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		buf.writeFloat(x);
		buf.writeFloat(y);
		buf.writeFloat(z);
		return this;
	}

}
