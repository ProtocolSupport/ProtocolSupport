package protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound;

import gnu.trove.map.hash.TIntObjectHashMap;
import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.transformer.mcpe.utils.PEDataWatcherSerializer;
import protocolsupport.utils.DataWatcherObject;
import protocolsupport.utils.DataWatcherObject.ValueType;

public class AddLivingEntityPacket implements ClientboundPEPacket {

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

	public AddLivingEntityPacket(int entityId, int type, float x, float y, float z, float yaw, float pitch) {
		this.entityId = entityId;
		this.type = type;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
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
		//TODO: actual metadata
		TIntObjectHashMap<DataWatcherObject> metadata = new TIntObjectHashMap<>();
		metadata.put(0, new DataWatcherObject(ValueType.BYTE, (byte) 0));
		metadata.put(1, new DataWatcherObject(ValueType.SHORT, (short) 300));
		metadata.put(2, new DataWatcherObject(ValueType.STRING, ""));
		metadata.put(3, new DataWatcherObject(ValueType.BYTE, (byte) 1));
		metadata.put(4, new DataWatcherObject(ValueType.BYTE, (byte) 0));
		metadata.put(15, new DataWatcherObject(ValueType.BYTE, (byte) 0));
		buf.writeBytes(PEDataWatcherSerializer.encode(metadata));
		//do not bother sending attach entity there, will send them as separate packet later
		buf.writeShort(0);
		return this;
	}

}
