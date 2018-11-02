package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockChangeMulti;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.BlockTileUpdate;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.basic.TileNBTRemapper;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.block.PreFlatteningBlockIdData;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.protocol.utils.types.TileEntityType;
import protocolsupport.protocol.utils.types.nbt.NBTCompound;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class BlockChangeMulti extends MiddleBlockChangeMulti {

	public BlockChangeMulti(ConnectionImpl connection) {
		super(connection);
	}

	protected final ArrayBasedIdRemappingTable blockRemappingTable = LegacyBlockData.REGISTRY.getTable(connection.getVersion());
	protected final TileNBTRemapper tileremapper = cache.getTileCache().getTileRemapper();

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_BLOCK_CHANGE_MULTI_ID);
		PositionSerializer.writeChunkCoord(serializer, chunk);
		ArraySerializer.writeVarIntTArray(serializer, records, (to, record) -> {
			PositionSerializer.writeLocalCoord(serializer, record.localCoord);
			VarNumberSerializer.writeVarInt(to, PreFlatteningBlockIdData.getCombinedId(blockRemappingTable.getRemap(record.id)));
			if (tileremapper.tileThatNeedsBlockstate(record.id)) {
				cache.getTileCache().setCachedTileBlockstate(chunk, record.localCoord, record.id);
			}
			if (tileremapper.usedToBeTile(record.id)) {
				NBTCompound tile = tileremapper.getLegacyTileFromBlock(Position.fromLocal(chunk, record.localCoord), record.id);
				if (tile != null) {
					packets.add(BlockTileUpdate.createPacketData(connection,
							TileEntityType.getByRegistryId(TileNBTRemapper.getTileType(tile)), 
							TileNBTRemapper.getPosition(tile),
							tile)
						);
				}
			}
		});
		packets.add(0, serializer);
		return packets;
	}

}
