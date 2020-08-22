package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockAction;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;

public class BlockAction extends MiddleBlockAction {

	protected final ArrayBasedIntMappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);

	public BlockAction(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData blockaction = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_BLOCK_ACTION);
		PositionSerializer.writeLegacyPositionL(blockaction, position);
		blockaction.writeByte(actionId);
		blockaction.writeByte(actionParam);
		VarNumberSerializer.writeVarInt(blockaction, BlockRemappingHelper.remapPreFlatteningBlockId(blockDataRemappingTable, blockId));
		codec.write(blockaction);
	}

}
