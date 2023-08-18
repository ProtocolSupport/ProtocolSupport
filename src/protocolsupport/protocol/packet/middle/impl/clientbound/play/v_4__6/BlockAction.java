package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__6;

import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleBlockAction;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.typeremapper.block.BlockDataLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;

public class BlockAction extends MiddleBlockAction implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6
{

	protected final ArrayBasedIntMappingTable blockDataRemappingTable = BlockDataLegacyDataRegistry.INSTANCE.getTable(version);

	public BlockAction(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData blockaction = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_BLOCK_ACTION);
		PositionCodec.writePositionISI(blockaction, position);
		blockaction.writeByte(actionId);
		blockaction.writeByte(actionParam);
		blockaction.writeShort(BlockRemappingHelper.remapPreFlatteningBlockId(blockDataRemappingTable, blockId));
		io.writeClientbound(blockaction);
	}

}
