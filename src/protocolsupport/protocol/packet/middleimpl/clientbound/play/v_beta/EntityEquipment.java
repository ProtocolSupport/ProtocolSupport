package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_beta;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityEquipment;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackRemapper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityEquipment extends MiddleEntityEquipment {

	public EntityEquipment(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		if (slot == 1) {
			return RecyclableEmptyList.get();
		} else {
			ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_ENTITY_EQUIPMENT_ID);
			serializer.writeInt(entityId);
			serializer.writeShort(slot == 0 ? slot : slot - 1);
			if (!itemstack.isNull()) {
				itemstack = ItemStackRemapper.remapToClient(version, cache.getAttributesCache().getLocale(), itemstack);
				serializer.writeShort(itemstack.getTypeId());
				serializer.writeShort(itemstack.getLegacyData());
			} else {
				serializer.writeShort(0);
				serializer.writeShort(0);
			}
			return RecyclableSingletonList.create(serializer);
		}
	}

}
