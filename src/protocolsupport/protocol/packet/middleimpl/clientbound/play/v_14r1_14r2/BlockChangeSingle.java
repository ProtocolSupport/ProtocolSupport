package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockChangeSingle;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.types.Position;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class BlockChangeSingle extends MiddleBlockChangeSingle {

	public BlockChangeSingle(ConnectionImpl connection) {
		super(connection);
	}

	protected final ArrayBasedIdRemappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
	protected final FlatteningBlockDataTable flatteningBlockDataTable = FlatteningBlockData.REGISTRY.getTable(version);

	@Override
	public RecyclableCollection<? extends IPacketData> toData() {
		return createBlockChangeSinglePacket(position, id, blockDataRemappingTable, flatteningBlockDataTable);
	}

	public static RecyclableCollection<? extends IPacketData> createBlockChangeSinglePacket(Position position, int id, ArrayBasedIdRemappingTable blockDataRemappingTable, FlatteningBlockDataTable flatteningBlockDataTable) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_BLOCK_CHANGE_SINGLE);
		PositionSerializer.writePosition(serializer, position);
		VarNumberSerializer.writeVarInt(serializer, BlockRemappingHelper.remapFBlockDataId(blockDataRemappingTable, flatteningBlockDataTable, id));
		return RecyclableSingletonList.create(serializer);
	}

}
