package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockChangeSingle;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.pe.PEBlocks;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.types.Position;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class BlockChangeSingle extends MiddleBlockChangeSingle {

	public BlockChangeSingle(ConnectionImpl connection) {
		super(connection);
	}

	protected static final int flag_update_neighbors = 0b0001;
	protected static final int flag_network = 0b0010;
	protected static final int flag_nographic = 0b0100;
	protected static final int flag_priority = 0b1000;

	protected static final int flags = (flag_update_neighbors | flag_network | flag_priority);

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		return BlockChangeSingle.create(connection.getVersion(), position, id, RecyclableArrayList.create());
	}

	public static RecyclableArrayList<ClientBoundPacketData> create(ProtocolVersion version, Position position, int runtimeId, RecyclableArrayList<ClientBoundPacketData> packets) {
		ClientBoundPacketData updateBlock = ClientBoundPacketData.create(PEPacketIDs.UPDATE_BLOCK);
		PositionSerializer.writePEPosition(updateBlock, position);
		VarNumberSerializer.writeVarInt(updateBlock, PEBlocks.getPocketRuntimeId(LegacyBlockData.REGISTRY.getTable(version).getRemap(runtimeId)));
		VarNumberSerializer.writeVarInt(updateBlock, flags);
		VarNumberSerializer.writeVarInt(updateBlock, 0); //Normal layer
		packets.add(0, updateBlock);
		//Waterlogged logic.
		if (PEBlocks.canPCBlockBeWaterLogged(runtimeId) || runtimeId == 0) {
			ClientBoundPacketData updateWater = ClientBoundPacketData.create(PEPacketIDs.UPDATE_BLOCK);
			PositionSerializer.writePEPosition(updateWater, position);
			VarNumberSerializer.writeVarInt(updateWater, PEBlocks.isPCBlockWaterlogged(runtimeId) ? PEBlocks.getPEWaterId(version) : 0);
			VarNumberSerializer.writeVarInt(updateWater, flags);
			VarNumberSerializer.writeVarInt(updateWater, 1); //Liquid layer
			packets.add(0, updateWater);
		}
		return packets;
	}

}
