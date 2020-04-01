package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_beta;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractBlockChangeMulti;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.block.PreFlatteningBlockIdData;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;

public class BlockChangeMulti extends AbstractBlockChangeMulti {

	public BlockChangeMulti(ConnectionImpl connection) {
		super(connection);
	}

	protected final ArrayBasedIdRemappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);

	@Override
	public void writeToClient() {
		ClientBoundPacketData blockchangemulti = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_BLOCK_CHANGE_MULTI);
		PositionSerializer.writeIntChunkCoord(blockchangemulti, chunkCoord);
		int rLength = records.length;
		blockchangemulti.writeShort(rLength);
		byte[] types = new byte[rLength];
		byte[] metadata = new byte[rLength];
		for (int i = 0; i < rLength; i++) {
			Record record = records[i];
			blockchangemulti.writeShort(record.coord);
			int blockdata = BlockRemappingHelper.remapPreFlatteningBlockDataNormal(blockDataRemappingTable, record.id);
			types[i] = (byte) PreFlatteningBlockIdData.getIdFromCombinedId(blockdata);
			metadata[i] = (byte) PreFlatteningBlockIdData.getDataFromCombinedId(blockdata);
		}
		blockchangemulti.writeBytes(types);
		blockchangemulti.writeBytes(metadata);
		codec.write(blockchangemulti);
	}

}
