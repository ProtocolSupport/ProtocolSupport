package protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;

public class MoveEntityPacket implements ClientboundPEPacket {

	protected int entityId;
	protected float x;
	protected float y;
	protected float z;
	protected float yaw;
	protected float pitch;

	public MoveEntityPacket(int entityId, float x, float y, float z, float yaw, float pitch) {
		this.entityId = entityId;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}

	@Override
	public int getId() {
		return PEPacketIDs.MOVE_ENTITY_PACKET;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		buf.writeInt(1);
		buf.writeLong(entityId);
		buf.writeFloat(x);
		buf.writeFloat(y);
		buf.writeFloat(z);
		buf.writeFloat(yaw);
		buf.writeFloat(yaw);
		buf.writeFloat(pitch);
		return this;
	}

}
