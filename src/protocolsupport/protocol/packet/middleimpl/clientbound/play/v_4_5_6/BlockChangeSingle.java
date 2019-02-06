package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockChangeSingle;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.BlockTileUpdate;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.storage.netcache.TileDataCache;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.block.PreFlatteningBlockIdData;
import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.utils.types.ChunkCoord;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class BlockChangeSingle extends MiddleBlockChangeSingle {

	public BlockChangeSingle(ConnectionImpl connection) {
		super(connection);
	}

	protected final ArrayBasedIdRemappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
	protected final TileEntityRemapper tileRemapper = TileEntityRemapper.getRemapper(version);

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ChunkCoord chunk = TileDataCache.getChunkCoordsFromPosition(position);
		int localcoord = TileDataCache.getLocalCoordFromPosition(position);
		if (tileRemapper.tileThatNeedsBlockData(id)) {
			cache.getTileCache().setBlockData(chunk, localcoord, id);
		} else {
			cache.getTileCache().removeBlockData(chunk, localcoord);
		}

		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();

		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_BLOCK_CHANGE_SINGLE_ID);
		PositionSerializer.writeLegacyPositionB(serializer, position);
		int lId = BlockRemappingHelper.remapBlockDataNormal(blockDataRemappingTable, id);
		serializer.writeShort(PreFlatteningBlockIdData.getIdFromCombinedId(lId));
		serializer.writeByte(PreFlatteningBlockIdData.getDataFromCombinedId(lId));
		packets.add(serializer);

		if (tileRemapper.usedToBeTile(id)) {
			packets.add(BlockTileUpdate.create(version, tileRemapper.getLegacyTileFromBlock(position, id)));
		}

		return packets;
	}

}
