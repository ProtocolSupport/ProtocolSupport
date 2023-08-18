package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_13;

import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV13;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__13.AbstractChunkCacheBlockChangeSingle;
import protocolsupport.protocol.typeremapper.block.BlockDataLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.types.Position;

public class BlockChangeSingle extends AbstractChunkCacheBlockChangeSingle implements IClientboundMiddlePacketV13 {

	public BlockChangeSingle(IMiddlePacketInit init) {
		super(init);
	}

	protected final ArrayBasedIntMappingTable blockLegacyDataTable = BlockDataLegacyDataRegistry.INSTANCE.getTable(version);
	protected final FlatteningBlockDataTable flatteningBlockDataTable = FlatteningBlockDataRegistry.INSTANCE.getTable(version);

	@Override
	protected void write() {
		io.writeClientbound(create(position, BlockRemappingHelper.remapFlatteningBlockDataId(blockLegacyDataTable, flatteningBlockDataTable, blockdata)));
	}

	public static ClientBoundPacketData create(Position position, int id) {
		ClientBoundPacketData blockchangesingle = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_BLOCK_CHANGE_SINGLE);
		PositionCodec.writePositionLXYZ(blockchangesingle, position);
		VarNumberCodec.writeVarInt(blockchangesingle, id);
		return blockchangesingle;
	}

}
