package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_17r1_17r2;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleBlockChangeMulti;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r2;
import protocolsupport.protocol.typeremapper.block.BlockDataLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;

public class BlockChangeMulti extends MiddleBlockChangeMulti implements
IClientboundMiddlePacketV17r1,
IClientboundMiddlePacketV17r2 {

	public BlockChangeMulti(IMiddlePacketInit init) {
		super(init);
	}

	protected final ArrayBasedIntMappingTable blockLegacyDataTable = BlockDataLegacyDataRegistry.INSTANCE.getTable(version);
	protected final FlatteningBlockDataTable flatteningBlockDataTable = FlatteningBlockDataRegistry.INSTANCE.getTable(version);

	@Override
	protected void write() {
		ClientBoundPacketData blockchangemulti = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_BLOCK_CHANGE_MULTI);
		blockchangemulti.writeLong(
			((chunkX & 0x3FFFFFL) << 42) |
			((chunkZ & 0x3FFFFFL) << 20) |
			(chunkSection & 0xFFFFF)
		);
		blockchangemulti.writeBoolean(skipLight);
		ArrayCodec.writeVarIntTArray(
			blockchangemulti, records,
			(recordTo, record) -> VarNumberCodec.writeVarLong(
				recordTo,
				(record.getRelX() << 8) |
				(record.getRelZ() << 4) |
				(record.getRelY()) |
				(BlockRemappingHelper.remapFlatteningBlockDataId(blockLegacyDataTable, flatteningBlockDataTable, record.getBlockData()) << 12)
			)
		);
		io.writeClientbound(blockchangemulti);
	}

}
