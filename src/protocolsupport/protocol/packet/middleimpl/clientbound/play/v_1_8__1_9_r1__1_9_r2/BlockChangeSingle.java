package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockChangeSingle;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class BlockChangeSingle extends MiddleBlockChangeSingle<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_BLOCK_CHANGE_SINGLE_ID, version);
		serializer.writePosition(position);
		serializer.writeVarInt(IdRemapper.BLOCK.getTable(version).getRemap(id));
		return RecyclableSingletonList.create(serializer);
	}

}
