package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleAcknowledgePlayerDigging;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2.BlockChangeSingle;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class AcknowledgePlayerDigging extends MiddleAcknowledgePlayerDigging {

	public AcknowledgePlayerDigging(ConnectionImpl connection) {
		super(connection);
	}

	protected final ArrayBasedIdRemappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
	protected final FlatteningBlockDataTable flatteningBlockDataTable = FlatteningBlockData.REGISTRY.getTable(version);

	@Override
	public boolean postFromServerRead() {
		return !successful;
	}

	@Override
	public RecyclableCollection<? extends IPacketData> toData() {
		return BlockChangeSingle.createBlockChangeSinglePacket(position, blockId, blockDataRemappingTable, flatteningBlockDataTable);
	}

}
