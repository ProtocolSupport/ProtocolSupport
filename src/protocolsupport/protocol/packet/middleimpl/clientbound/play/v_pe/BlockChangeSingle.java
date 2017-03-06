package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.PEPacketIDs;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockChangeSingle;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class BlockChangeSingle extends MiddleBlockChangeSingle {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		return RecyclableSingletonList.create(BlockChangeSingle.toPEUpdateBlockPacket(version, position, id));
	}
	
	public static ClientBoundPacketData toPEUpdateBlockPacket(ProtocolVersion version, protocolsupport.protocol.utils.types.Position position, int id) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.UPDATE_BLOCK, version);
		VarNumberSerializer.writeSVarInt(serializer, position.getX());
		VarNumberSerializer.writeVarInt(serializer, position.getY());
		VarNumberSerializer.writeSVarInt(serializer, position.getZ());
		int type = id >> 4;
		int meta = id & 15;
		VarNumberSerializer.writeVarInt(serializer, type);
		VarNumberSerializer.writeVarInt(serializer, (0b1000 << 4) | meta); // 0b1000 = Priority // TODO: Flags (are they even used anymore?)
		return serializer;
	}

}
