package protocolsupport.protocol.packet.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middlepacket.clientbound.play.MiddleHeldSlot;
import protocolsupport.protocol.packet.middlepacketimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class HeldSlot extends MiddleHeldSlot<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_HELD_SLOT_ID, version);
		serializer.writeShort(slot);
		return RecyclableSingletonList.create(serializer);
	}

}
