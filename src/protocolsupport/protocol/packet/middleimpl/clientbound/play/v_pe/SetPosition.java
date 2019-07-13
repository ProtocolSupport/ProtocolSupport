package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import org.bukkit.util.NumberConversions;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSetPosition;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.login.v_pe.LoginSuccess;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.MovementCache;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.NetworkEntityDataCache;
import protocolsupport.protocol.types.ChunkCoord;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class SetPosition extends MiddleSetPosition {

	public SetPosition(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		MovementCache movecache = cache.getMovementCache();
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		//PE sends position that intersects blocks bounding boxes in some cases
		//Server doesn't accept such movements and will send a set position, but we ignore it unless it is above leniency
		if (movecache.isPEPositionAboveLeniency()) {
			ChunkCoord chunk = new ChunkCoord(NumberConversions.floor(x) >> 4, NumberConversions.floor(z) >> 4);
			if (!cache.getPEChunkMapCache().isMarkedAsSent(chunk)) {
				Chunk.addFakeChunks(packets, chunk, version);
			}
			byte headYaw = cache.getWatchedEntityCache().getSelfPlayer().getDataCache().getHeadRotation((byte) 0);
			packets.add(create(
				cache.getWatchedEntityCache().getSelfPlayer(), (float) x, (float) y + 0.01f, (float) z,
				pitch * 360.F / 256.F, yaw * 360.F / 256.F, headYaw * 360.F / 256.F, ANIMATION_MODE_TELEPORT
			));
		}
		movecache.setPEClientPosition(x, y, z);
		if (movecache.peNeedsPlayerSpawn()) {
			packets.add(LoginSuccess.createPlayStatus(LoginSuccess.PLAYER_SPAWN));
			movecache.setPeNeedsPlayerSpawn(false);
		}
		return packets;
	}

	@Override
	public boolean postFromServerRead() {
		if (teleportConfirmId != 0) {
			cache.getMovementCache().setTeleportLocation(x, y, z, teleportConfirmId);
		}
		return true;
	}

	public static final int ANIMATION_MODE_ALL = 0;
	public static final int ANIMATION_MODE_TELEPORT = 2;
	public static final int ANIMATION_MODE_PITCH = 3;

	public static ClientBoundPacketData createPitch(NetworkEntity entity) {
		NetworkEntityDataCache cache = entity.getDataCache();
		return create(
			entity, 0, 0, 0,
			cache.getPitch() * 360.F / 256.F, 0,
			cache.getHeadRotation(cache.getYaw()) * 360.F / 256.F, ANIMATION_MODE_PITCH
		);
	}

	public static ClientBoundPacketData createAll(NetworkEntity entity) {
		NetworkEntityDataCache cache = entity.getDataCache();
		return create(entity, cache.getPosX(), cache.getPosY(), cache.getPosZ(),
			cache.getPitch() * 360.F / 256.F, cache.getYaw() * 360.F / 256.F,
			cache.getHeadRotation(cache.getYaw()) * 360.F / 256.F, ANIMATION_MODE_ALL);
	}

	public static ClientBoundPacketData create(NetworkEntity entity, float x, float y, float z, float pitch, float yaw, float headYaw, int mode) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.PLAYER_MOVE);
		VarNumberSerializer.writeVarLong(serializer, entity.getId());
		serializer.writeFloatLE(x);
		serializer.writeFloatLE(y + 1.62f);
		serializer.writeFloatLE(z);
		serializer.writeFloatLE(pitch);
		serializer.writeFloatLE(yaw);
		serializer.writeFloatLE(headYaw);
		serializer.writeByte(mode);
		serializer.writeBoolean(false); //TODO: onGround
		VarNumberSerializer.writeVarLong(serializer, entity.getDataCache().getVehicleId());
		if (mode == ANIMATION_MODE_TELEPORT) {
			serializer.writeIntLE(0); //teleportCause
			serializer.writeIntLE(0); //teleportItem
		}
		return serializer;
	}

}
