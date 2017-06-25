package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.UUID;

import org.bukkit.util.Vector;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.types.NetworkEntity;

public abstract class MiddleSpawnObject extends ClientBoundMiddlePacket {

	protected NetworkEntity entity;
	protected double x;
	protected double y;
	protected double z;
	protected int pitch;
	protected int yaw;
	protected int objectdata;
	protected int motX;
	protected int motY;
	protected int motZ;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		int entityId = VarNumberSerializer.readVarInt(serverdata);
		UUID uuid = MiscSerializer.readUUID(serverdata);
		int typeId = serverdata.readUnsignedByte();
		x = serverdata.readDouble();
		y = serverdata.readDouble();
		z = serverdata.readDouble();
		pitch = serverdata.readUnsignedByte();
		yaw = serverdata.readUnsignedByte();
		objectdata = serverdata.readInt();
		motX = serverdata.readShort();
		motY = serverdata.readShort();
		motZ = serverdata.readShort();
		entity = NetworkEntity.createObject(uuid, entityId, typeId, objectdata);
	}

	@Override
	public void handle() {
		cache.addWatchedEntity(entity, new Vector(x, y, z), (byte) yaw, (byte) pitch);
	}

}
