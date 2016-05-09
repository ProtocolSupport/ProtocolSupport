package protocolsupport.protocol.packet.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middlepacket.clientbound.play.MiddleBlockAction;
import protocolsupport.protocol.packet.middlepacketimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class BlockAction extends MiddleBlockAction<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_BLOCK_ACTION_ID, version);
		serializer.writeInt(position.getX());
		serializer.writeShort(position.getY());
		serializer.writeInt(position.getZ());
		serializer.writeByte(info1);
		serializer.writeByte(info2);
		serializer.writeShort(type);
		return RecyclableSingletonList.create(serializer);
	}

}
