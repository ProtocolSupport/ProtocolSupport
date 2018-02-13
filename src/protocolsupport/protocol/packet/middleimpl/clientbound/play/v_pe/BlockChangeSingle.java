package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockChangeSingle;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.protocol.utils.types.Position;

public class BlockChangeSingle extends MiddleBlockChangeSingle {

    private static final int flag_update_neighbors = 0b0001;
    private static final int flag_network = 0b0010;
    private static final int flag_priority = 0b1000;

    private static final int flags = (flag_update_neighbors | flag_network | flag_priority);

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		System.out.println("single block!");
		return RecyclableSingletonList.create(BlockChangeSingle.create(connection.getVersion(), position, id));
	}

	public static ClientBoundPacketData create(ProtocolVersion version, Position position, int state) {
		state = PEDataValues.BLOCK_ID.getRemap(IdRemapper.BLOCK.getTable(version).getRemap(state));
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.UPDATE_BLOCK, version);
		PositionSerializer.writePEPosition(serializer, position);
		VarNumberSerializer.writeVarInt(serializer, MinecraftData.getBlockIdFromState(state));
		VarNumberSerializer.writeVarInt(serializer, (flags << 4) | MinecraftData.getBlockDataFromState(state));
		return serializer;
	}

}
