package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_20;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleBlockChangeMulti;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV20;
import protocolsupport.protocol.typeremapper.block.BlockDataLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;

public class BlockChangeMulti extends MiddleBlockChangeMulti implements
IClientboundMiddlePacketV20 {

	public BlockChangeMulti(IMiddlePacketInit init) {
		super(init);
	}

	protected final ArrayBasedIntMappingTable blockLegacyDataTable = BlockDataLegacyDataRegistry.INSTANCE.getTable(version);
	protected final FlatteningBlockDataTable flatteningBlockDataTable = FlatteningBlockDataRegistry.INSTANCE.getTable(version);

	@Override
	protected void write() {
		ClientBoundPacketData blockchangemultiPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_BLOCK_CHANGE_MULTI);
		blockchangemultiPacket.writeLong(
			((chunkX & 0x3FFFFFL) << 42) |
			((chunkZ & 0x3FFFFFL) << 20) |
			(chunkSection & 0xFFFFF)
		);
		ArrayCodec.writeVarIntTArray(
			blockchangemultiPacket, records,
			(recordTo, record) -> VarNumberCodec.writeVarLong(
				recordTo,
				(record.getRelX() << 8) |
				(record.getRelZ() << 4) |
				(record.getRelY()) |
				(BlockRemappingHelper.remapFlatteningBlockDataId(blockLegacyDataTable, flatteningBlockDataTable, record.getBlockData()) << 12)
			)
		);
		io.writeClientbound(blockchangemultiPacket);
	}

}
