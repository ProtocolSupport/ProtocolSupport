package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.UUID;

import org.bukkit.util.Vector;

import gnu.trove.map.TIntObjectMap;
import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherDeserializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.types.NetworkEntity;

public abstract class MiddleSpawnLiving extends ClientBoundMiddlePacket {

	protected NetworkEntity entity;
	protected double x;
	protected double y;
	protected double z;
	protected int yaw;
	protected int pitch;
	protected int headPitch;
	protected int motX;
	protected int motY;
	protected int motZ;
	protected TIntObjectMap<DataWatcherObject<?>> metadata;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		int entityId = VarNumberSerializer.readVarInt(serverdata);
		UUID uuid = MiscSerializer.readUUID(serverdata);
		int typeId = VarNumberSerializer.readVarInt(serverdata);
		entity = NetworkEntity.createMob(uuid, entityId, typeId);
		x = serverdata.readDouble();
		y = serverdata.readDouble();
		z = serverdata.readDouble();
		yaw = serverdata.readUnsignedByte();
		pitch = serverdata.readUnsignedByte();
		headPitch = serverdata.readUnsignedByte();
		motX = serverdata.readShort();
		motY = serverdata.readShort();
		motZ = serverdata.readShort();
		metadata = DataWatcherDeserializer.decodeData(serverdata, ProtocolVersionsHelper.LATEST_PC);
	}

	@Override
	public void handle() {
		cache.addWatchedEntity(entity, new Vector(x, y, z), (byte) yaw, (byte) pitch);
	}

}
