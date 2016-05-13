package protocolsupport.protocol.packet.mcpe.packet.mcpe.clientbound;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.PEPacketIDs;

public class EntityStatusPacket implements ClientboundPEPacket {

	protected int entityId;
	protected int action;

	public EntityStatusPacket(int entityId, int action) {
		this.entityId = entityId;
		this.action = action;
	}

	@Override
	public int getId() {
		return PEPacketIDs.ENTITY_EVENT_PACKET;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		buf.writeLong(entityId);
		buf.writeByte(action);
		return this;
	}

}
