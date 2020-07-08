package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractChunkCacheBlockChangeSingle;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.block.PreFlatteningBlockIdData;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.types.Position;

public class BlockChangeSingle extends AbstractChunkCacheBlockChangeSingle {

	public BlockChangeSingle(ConnectionImpl connection) {
		super(connection);
	}

	protected final ArrayBasedIntMappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);

	@Override
	protected void writeToClient() {
		codec.write(create(position, BlockRemappingHelper.remapPreFlatteningBlockDataNormal(blockDataRemappingTable, id)));
	}

	public static ClientBoundPacketData create(Position position, int id) {
		ClientBoundPacketData blockchangesingle = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_BLOCK_CHANGE_SINGLE);
		PositionSerializer.writeLegacyPositionB(blockchangesingle, position);
		blockchangesingle.writeShort(PreFlatteningBlockIdData.getIdFromCombinedId(id));
		blockchangesingle.writeByte(PreFlatteningBlockIdData.getDataFromCombinedId(id));
		return blockchangesingle;
	}

}
