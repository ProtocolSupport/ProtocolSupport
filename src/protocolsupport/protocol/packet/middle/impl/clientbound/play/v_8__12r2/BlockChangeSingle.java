package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_8__12r2;

import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV10;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV11;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__13.AbstractChunkCacheBlockChangeSingle;
import protocolsupport.protocol.typeremapper.block.BlockDataLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.types.Position;

public class BlockChangeSingle extends AbstractChunkCacheBlockChangeSingle implements
IClientboundMiddlePacketV8,
IClientboundMiddlePacketV9r1,
IClientboundMiddlePacketV9r2,
IClientboundMiddlePacketV10,
IClientboundMiddlePacketV11,
IClientboundMiddlePacketV12r1,
IClientboundMiddlePacketV12r2 {

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
		PositionCodec.writePositionLXYZ(blockchangesingle, position);
		VarNumberCodec.writeVarInt(blockchangesingle, id);
		return blockchangesingle;
	}

}
