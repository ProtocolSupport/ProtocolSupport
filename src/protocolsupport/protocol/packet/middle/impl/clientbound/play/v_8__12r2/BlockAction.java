package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_8__12r2;

import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleBlockAction;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV10;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV11;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r2;
import protocolsupport.protocol.typeremapper.block.BlockDataLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;

public class BlockAction extends MiddleBlockAction implements
IClientboundMiddlePacketV8,
IClientboundMiddlePacketV9r1,
IClientboundMiddlePacketV9r2,
IClientboundMiddlePacketV10,
IClientboundMiddlePacketV11,
IClientboundMiddlePacketV12r1,
IClientboundMiddlePacketV12r2 {

	protected final ArrayBasedIntMappingTable blockDataRemappingTable = BlockDataLegacyDataRegistry.INSTANCE.getTable(version);

	public BlockAction(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData blockaction = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_BLOCK_ACTION);
		PositionCodec.writePositionLXYZ(blockaction, position);
		blockaction.writeByte(actionId);
		blockaction.writeByte(actionParam);
		VarNumberCodec.writeVarInt(blockaction, BlockRemappingHelper.remapPreFlatteningBlockId(blockDataRemappingTable, blockId));
		io.writeClientbound(blockaction);
	}

}
