package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityEquipment;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;

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
			ItemStackSerializer.writeItemStack(entityequipment, version, cache.getAttributesCache().getLocale(), itemstack);
			codec.write(entityequipment);
		}
	}

}
