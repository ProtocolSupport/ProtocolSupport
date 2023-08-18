package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_7;

import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__13.AbstractChunkCacheBlockChangeSingle;
import protocolsupport.protocol.typeremapper.block.BlockDataLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.PreFlatteningBlockIdData;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.types.Position;

public class BlockChangeSingle extends AbstractChunkCacheBlockChangeSingle implements IClientboundMiddlePacketV7 {

	public BlockChangeSingle(IMiddlePacketInit init) {
		super(init);
	}

	protected final ArrayBasedIntMappingTable blockLegacyDataTable = BlockDataLegacyDataRegistry.INSTANCE.getTable(version);

	@Override
	protected void write() {
		io.writeClientbound(create(position, BlockRemappingHelper.remapPreFlatteningBlockDataNormal(blockLegacyDataTable, blockdata)));
	}

	public static ClientBoundPacketData create(Position position, int id) {
		ClientBoundPacketData blockchangesingle = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_BLOCK_CHANGE_SINGLE);
		PositionCodec.writePositionIBI(blockchangesingle, position);
		VarNumberCodec.writeVarInt(blockchangesingle, PreFlatteningBlockIdData.getIdFromCombinedId(id));
		blockchangesingle.writeByte(PreFlatteningBlockIdData.getDataFromCombinedId(id));
		return blockchangesingle;
	}

}
