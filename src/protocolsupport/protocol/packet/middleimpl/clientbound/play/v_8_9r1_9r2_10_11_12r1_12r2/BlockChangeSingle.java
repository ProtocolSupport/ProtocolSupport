package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractBlockChangeSingle;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.types.Position;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class BlockChangeSingle extends AbstractBlockChangeSingle {

	public BlockChangeSingle(ConnectionImpl connection) {
		super(connection);
	}

	protected final ArrayBasedIdRemappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);

	@Override
	public RecyclableCollection<? extends IPacketData> toData() {
		return createBlockChangeSinglePacket(position, id, blockDataRemappingTable);
	}

	public static RecyclableCollection<? extends IPacketData> createBlockChangeSinglePacket(Position position, int id, ArrayBasedIdRemappingTable blockDataRemappingTable) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_BLOCK_CHANGE_SINGLE);
		PositionSerializer.writeLegacyPositionL(serializer, position);
		VarNumberSerializer.writeVarInt(serializer, BlockRemappingHelper.remapBlockDataNormal(blockDataRemappingTable, id));
		return RecyclableSingletonList.create(serializer);
	}

}
