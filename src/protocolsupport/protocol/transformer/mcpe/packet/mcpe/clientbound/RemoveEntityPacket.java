package protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;

public class RemoveEntityPacket implements ClientboundPEPacket {

	public RemoveEntityPacket(int entityId) {
		this.entityId = entityId;
	}

	protected int entityId;

	@Override
	public int getId() {
		return PEPacketIDs.REMOVE_ENTITY_PACKET;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		buf.writeLong(entityId);
		return this;
	}

}
