package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityEquipment;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityEquipment extends MiddleEntityEquipment<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) {
		if (slot == 1) {
			return RecyclableEmptyList.get();
		} else {
			PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_ENTITY_EQUIPMENT_ID, version);
			serializer.writeVarInt(entityId);
			serializer.writeShort(slot == 0 ? slot : slot - 1);
			serializer.writeItemStack(itemstack);
			return RecyclableSingletonList.create(serializer);
		}
	}

}
