package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r2_15_16;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockBreakConfirm;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;

public class BlockBreakConfirm extends MiddleBlockBreakConfirm {

	public BlockBreakConfirm(ConnectionImpl connection) {
		super(connection);
	}

	protected final ArrayBasedIdRemappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
	protected final FlatteningBlockDataTable flatteningBlockDataTable = FlatteningBlockData.REGISTRY.getTable(version);

	@Override
	protected void writeToClient() {
		ClientBoundPacketData blockbreakconfirm = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_BLOCK_BREAK_CONFIRM);
		PositionSerializer.writePosition(blockbreakconfirm, position);
		VarNumberSerializer.writeVarInt(blockbreakconfirm, BlockRemappingHelper.remapFlatteningBlockDataId(blockDataRemappingTable, flatteningBlockDataTable, blockId));
		VarNumberSerializer.writeVarInt(blockbreakconfirm, status);
		blockbreakconfirm.writeBoolean(successful);
		codec.write(blockbreakconfirm);
	}

}
