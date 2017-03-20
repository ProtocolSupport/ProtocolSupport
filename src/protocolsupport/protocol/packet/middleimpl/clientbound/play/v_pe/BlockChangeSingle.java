package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.legacyremapper.pe.PEPacketIDs;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockChangeSingle;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class BlockChangeSingle extends MiddleBlockChangeSingle {

    private static final int flag_update_neighbors = 0b0001;
    private static final int flag_network = 0b0010;
    private static final int flag_prioirty = 0b1000;

    private static final int flags = (flag_update_neighbors | flag_network | flag_prioirty);

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		return RecyclableSingletonList.create(BlockChangeSingle.create(version, position, id));
	}

	public static ClientBoundPacketData create(ProtocolVersion version, protocolsupport.protocol.utils.types.Position position, int id) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.UPDATE_BLOCK, version);
		VarNumberSerializer.writeSVarInt(serializer, position.getX());
		VarNumberSerializer.writeVarInt(serializer, position.getY());
		VarNumberSerializer.writeSVarInt(serializer, position.getZ());
		int type = id >> 4;
		int meta = id & 15;
		VarNumberSerializer.writeVarInt(serializer, type);
		VarNumberSerializer.writeVarInt(serializer, (flags << 4) | meta);
		return serializer;
	}

}
