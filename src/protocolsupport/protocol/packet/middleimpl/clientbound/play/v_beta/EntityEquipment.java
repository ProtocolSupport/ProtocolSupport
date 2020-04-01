package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_beta;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityEquipment;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackRemapper;

public class EntityEquipment extends MiddleEntityEquipment {

	public EntityEquipment(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		if (slot != 1) {
			ClientBoundPacketData entityequipment = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_EQUIPMENT);
			entityequipment.writeInt(entityId);
			entityequipment.writeShort(slot == 0 ? slot : slot - 1);
			if (!itemstack.isNull()) {
				itemstack = ItemStackRemapper.remapToClient(version, cache.getAttributesCache().getLocale(), itemstack);
				entityequipment.writeShort(itemstack.getTypeId());
				entityequipment.writeShort(itemstack.getLegacyData());
			} else {
				entityequipment.writeShort(0);
				entityequipment.writeShort(0);
			}
			codec.write(entityequipment);
		}
	}

}
