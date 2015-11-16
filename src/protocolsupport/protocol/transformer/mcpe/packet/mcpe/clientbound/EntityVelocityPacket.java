package protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;

public class EntityVelocityPacket implements ClientboundPEPacket {

	protected int entityId;
	protected float x;
	protected float y;
	protected float z;

	public EntityVelocityPacket(int entityId, float x, float y, float z) {
		this.entityId = entityId;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public int getId() {
		return PEPacketIDs.SET_ENTITY_MOTION_PACKET;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		buf.writeInt(1);
		buf.writeLong(entityId);
		buf.writeFloat(x);
		buf.writeFloat(y);
		buf.writeFloat(z);
		return this;
	}

}
