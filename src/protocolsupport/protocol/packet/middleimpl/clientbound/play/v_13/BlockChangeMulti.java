package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractBlockChangeMulti;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;

public class BlockChangeMulti extends AbstractBlockChangeMulti {

	public BlockChangeMulti(ConnectionImpl connection) {
		super(connection);
	}

	protected final ArrayBasedIdRemappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
	protected final FlatteningBlockDataTable flatteningBlockDataTable = FlatteningBlockData.REGISTRY.getTable(version);

	@Override
	public void writeToClient() {
		ClientBoundPacketData blockchangemulti = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_BLOCK_CHANGE_MULTI);
		PositionSerializer.writeIntChunkCoord(blockchangemulti, chunkCoord);
		ArraySerializer.writeVarIntTArray(blockchangemulti, records, (to, record) -> {
			to.writeShort(record.coord);
			VarNumberSerializer.writeVarInt(to, BlockRemappingHelper.remapFlatteningBlockDataId(blockDataRemappingTable, flatteningBlockDataTable, record.id));
		});
		codec.write(blockchangemulti);
	}

}
