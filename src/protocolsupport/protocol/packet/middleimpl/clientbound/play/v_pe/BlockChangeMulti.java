package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockChangeMulti;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.BlockTileUpdate;
import protocolsupport.protocol.typeremapper.basic.TileNBTRemapper;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class BlockChangeMulti extends MiddleBlockChangeMulti {

	public BlockChangeMulti(ConnectionImpl connection) {
		super(connection);
	}

	protected final TileNBTRemapper tileremapper = TileNBTRemapper.getRemapper(connection.getVersion());

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		Int2IntMap tilestates = cache.getTileCache().getChunk(chunk);
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		for (Record record : records) {
			BlockChangeSingle.create(connection.getVersion(), Position.fromLocal(chunk, record.localCoord), record.id, packets);
			if (tileremapper.tileThatNeedsBlockData(record.id)) {
				tilestates.put(record.localCoord, record.id);
			} else {
				tilestates.remove(record.localCoord);
			}
			if (tileremapper.usedToBeTile(record.id)) {
				packets.add(BlockTileUpdate.create(
					connection, tileremapper.getLegacyTileFromBlock(Position.fromLocal(chunk, record.localCoord), record.id)
				));
			}
		}
		return packets;
	}

}