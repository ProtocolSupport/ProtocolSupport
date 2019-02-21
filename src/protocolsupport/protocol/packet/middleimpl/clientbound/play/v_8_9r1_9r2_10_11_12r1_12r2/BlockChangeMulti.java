package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockChangeMulti;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.BlockTileUpdate;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.TileDataCache;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class BlockChangeMulti extends MiddleBlockChangeMulti {

	public BlockChangeMulti(ConnectionImpl connection) {
		super(connection);
	}

	protected final ArrayBasedIdRemappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
	protected final TileEntityRemapper tileRemapper = TileEntityRemapper.getRemapper(version);

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		Int2IntMap tilestates = cache.getTileCache().getChunk(chunk);

		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();

		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_BLOCK_CHANGE_MULTI_ID);
		PositionSerializer.writeChunkCoord(serializer, chunk);
		ArraySerializer.writeVarIntTArray(serializer, records, (to, record) -> {
			int localcoord = TileDataCache.createLocalPositionFromMultiChangeBlock(record.coord);
			if (tileRemapper.tileThatNeedsBlockData(record.id)) {
				tilestates.put(localcoord, record.id);
			} else {
				tilestates.remove(localcoord);
			}

			serializer.writeShort(record.coord);
			VarNumberSerializer.writeVarInt(to, BlockRemappingHelper.remapBlockDataNormal(blockDataRemappingTable, record.id));

			if (tileRemapper.usedToBeTile(record.id)) {
				packets.add(BlockTileUpdate.create(
					version, tileRemapper.getLegacyTileFromBlock(getGlobalPosition(chunk, record.coord), record.id))
				);
			}
		});
		packets.add(0, serializer);

		return packets;
	}

}
