package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityEquipment;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class EntityEquipment extends MiddleEntityEquipment {

	public EntityEquipment(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		if (slot != 1) {
			ClientBoundPacketData entityequipment = codec.allocClientBoundPacketData(PacketType.CLIENTBOUND_PLAY_ENTITY_EQUIPMENT);
			VarNumberSerializer.writeVarInt(entityequipment, entityId);
			entityequipment.writeShort(slot == 0 ? slot : slot - 1);
			ItemStackSerializer.writeItemStack(entityequipment, version, cache.getAttributesCache().getLocale(), itemstack);
			codec.write(entityequipment);
		}
	}

}
