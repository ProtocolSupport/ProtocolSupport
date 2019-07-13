package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

import io.netty.buffer.ByteBuf;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChangeDimension;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.types.ChunkCoord;
import protocolsupport.protocol.types.Environment;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.WindowType;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class ChangeDimension extends MiddleChangeDimension {

	protected Set<Long> entityRemovals = new HashSet<>();

	public ChangeDimension(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public boolean postFromServerRead() {
		entityRemovals.clear();
		cache.getWatchedEntityCache().forEach(
			(id, entity) -> entityRemovals.add(id.longValue()));
		cache.getPEChunkMapCache().clear();
		return super.postFromServerRead();
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		if (cache.getWindowCache().getOpenedWindow() != WindowType.PLAYER) {
			packets.add(InventoryClose.create(connection.getVersion(), cache.getWindowCache().getOpenedWindowId()));
			cache.getWindowCache().closeWindow();
		}
		entityRemovals.forEach(id -> packets.add(EntityDestroy.create(id)));
		packets.add(createRaw(0, 0, 0, getPeDimensionId(dimension)));
		packets.add(SetViewCenter.createChunkPublisherUpdate(0, 0, 0));
		//needs a few chunks before the dim switch ack can confirm
		Chunk.addFakeChunks(packets, new ChunkCoord(0, 0), version);
		packets.add(ClientBoundPacketData.create(PEPacketIDs.EXT_PS_AWAIT_DIM_SWITCH_ACK));
		final Position pos = cache.getMovementCache().getChunkPublisherPosition();
		packets.add(SetViewCenter.createChunkPublisherUpdate(pos.getX(), pos.getY(), pos.getZ()));
		cache.getMovementCache().setPeNeedsPlayerSpawn(true);
		return packets;
	}

	public static void writeRaw(ByteBuf out, float x, float y, float z, int dimension) {
		VarNumberSerializer.writeSVarInt(out, dimension);
		out.writeFloatLE(x); //x
		out.writeFloatLE(y); //y
		out.writeFloatLE(z); //z
		out.writeBoolean(true); //respawn
	}

	public static ClientBoundPacketData createRaw(float x, float y, float z, int dimension) {
		ClientBoundPacketData changedim = ClientBoundPacketData.create(PEPacketIDs.CHANGE_DIMENSION);
		writeRaw(changedim, x, y, z, dimension);
		return changedim;
	}

	public static int getPeDimensionId(Environment dimId) {
		switch (dimId) {
			case NETHER: {
				return 1;
			}
			case THE_END: {
				return 2;
			}
			case OVERWORLD: {
				return 0;
			}
			default: {
				throw new IllegalArgumentException(MessageFormat.format("Uknown dim id {0}", dimId));
			}
		}
	}

}
