package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockChangeMulti;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.BlockTileUpdate;
import protocolsupport.protocol.typeremapper.basic.TileNBTRemapper;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.block.PreFlatteningBlockIdData;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.utils.types.TileEntityType;
import protocolsupport.protocol.utils.types.nbt.NBTCompound;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class BlockChangeMulti extends MiddleBlockChangeMulti {

	public BlockChangeMulti(ConnectionImpl connection) {
		super(connection);
	}

	protected final ArrayBasedIdRemappingTable blockRemappingTable = LegacyBlockData.REGISTRY.getTable(connection.getVersion());
	protected final TileNBTRemapper tileremapper = cache.getTileRemapper(connection);

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_BLOCK_CHANGE_MULTI_ID);
		serializer.writeInt(chunkX);
		serializer.writeInt(chunkZ);
		serializer.writeShort(records.length);
		serializer.writeInt(records.length * 4);
		for (Record record : records) {
			serializer.writeByte(record.horizCoord);
			serializer.writeByte(record.yCoord);
			serializer.writeShort(PreFlatteningBlockIdData.getCombinedId(blockRemappingTable.getRemap(record.id)));
			if (tileremapper.tileThatNeedsBlockstate(record.id)) {
				tileremapper.setTileBlockstate(fromLocalPosition(record), record.id);
			}
			if (tileremapper.usedToBeTile(record.id)) {
				NBTCompound tile = tileremapper.getLegacyTileFromBlock(record.id);
				packets.add(BlockTileUpdate.createPacketData(connection,
					TileEntityType.getByRegistryId(TileNBTRemapper.getTileType(tile)), 
					TileNBTRemapper.getPosition(tile),
					tile)
				);
			}
		}
		packets.add(serializer);
		return packets;
	}

}
