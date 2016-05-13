package protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound;

import gnu.trove.map.TIntObjectMap;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.transformer.mcpe.utils.PEDataWatcherSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public class AddEntityPacket implements ClientboundPEPacket {

	protected int entityId;
	protected int type;
	protected float x;
	protected float y;
	protected float z;
	protected float speedX;
	protected float speedY;
	protected float speedZ;
	protected float yaw;
	protected float pitch;
	protected TIntObjectMap<DataWatcherObject<?>> meta;

	public AddEntityPacket(int entityId, int type, float x, float y, float z, float yaw, float pitch, float speedX, float speedY, float speedZ, TIntObjectMap<DataWatcherObject<?>> meta) {
		this.entityId = entityId;
		this.type = type;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
		this.speedX = speedX;
		this.speedY = speedY;
		this.speedZ = speedZ;
		this.meta = meta;
	}

	@Override
	public int getId() {
		return PEPacketIDs.ADD_ENTITY_PACKET;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		buf.writeLong(entityId);
		buf.writeInt(type);
		buf.writeFloat(x);
		buf.writeFloat(y);
		buf.writeFloat(z);
		buf.writeFloat(speedX);
		buf.writeFloat(speedY);
		buf.writeFloat(speedZ);
		buf.writeFloat(yaw);
		buf.writeFloat(pitch);
		buf.writeBytes(PEDataWatcherSerializer.encode(meta));
		//do not bother sending attach entity there, will send them as separate packet later
		buf.writeShort(0);
		return this;
	}

}
