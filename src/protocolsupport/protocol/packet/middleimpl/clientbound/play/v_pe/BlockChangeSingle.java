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
	    ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.UPDATE_BLOCK, version);
		VarNumberSerializer.writeSVarInt(serializer, position.getX());
		VarNumberSerializer.writeVarInt(serializer, position.getY());
		VarNumberSerializer.writeSVarInt(serializer, position.getZ());
		int type = id >> 4;
		int meta = id & 15;
		VarNumberSerializer.writeVarInt(serializer, type);
		VarNumberSerializer.writeVarInt(serializer, meta); // TODO: Flags (are they even used anymore?)
		return RecyclableSingletonList.create(serializer);
	}

}
