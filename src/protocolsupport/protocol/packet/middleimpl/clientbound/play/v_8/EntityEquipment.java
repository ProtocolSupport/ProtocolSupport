package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityEquipment;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityEquipment extends MiddleEntityEquipment {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		if (slot == 1) {
			return RecyclableEmptyList.get();
		} else {
			ProtocolVersion version = connection.getVersion();
			ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_ENTITY_EQUIPMENT_ID, version);
			VarNumberSerializer.writeVarInt(serializer, entityId);
			serializer.writeShort(slot == 0 ? slot : slot - 1);
			ItemStackSerializer.writeItemStack(serializer, version, cache.getLocale(), itemstack, true);
			return RecyclableSingletonList.create(serializer);
		}
	}

}
