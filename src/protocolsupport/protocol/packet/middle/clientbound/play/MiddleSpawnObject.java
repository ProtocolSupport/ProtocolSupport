package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.SpecificRemapper;
import protocolsupport.protocol.typeremapper.watchedentity.types.WatchedObject;

public abstract class MiddleSpawnObject extends ClientBoundMiddlePacket {

	protected int entityId;
	protected UUID uuid;
	protected int type;
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
		entityId = VarNumberSerializer.readVarInt(serverdata);
		uuid = MiscSerializer.readUUID(serverdata);
		type = serverdata.readUnsignedByte();
		x = serverdata.readDouble();
		y = serverdata.readDouble();
		z = serverdata.readDouble();
		pitch = serverdata.readUnsignedByte();
		yaw = serverdata.readUnsignedByte();
		objectdata = serverdata.readInt();
		motX = serverdata.readShort();
		motY = serverdata.readShort();
		motZ = serverdata.readShort();
	}

	@Override
	public void handle() {
		cache.addWatchedEntity(new WatchedObject(entityId, type));
	}

	@Override
	public boolean isValid() {
		return SpecificRemapper.getObjectByTypeId(type) != SpecificRemapper.NONE;
	}

}
