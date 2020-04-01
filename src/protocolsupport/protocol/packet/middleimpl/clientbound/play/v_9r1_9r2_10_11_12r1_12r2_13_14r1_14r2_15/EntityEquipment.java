package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15;

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
		ClientBoundPacketData entityequipment = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_EQUIPMENT);
		VarNumberSerializer.writeVarInt(entityequipment, entityId);
		VarNumberSerializer.writeVarInt(entityequipment, slot);
		ItemStackSerializer.writeItemStack(entityequipment, version, cache.getAttributesCache().getLocale(), itemstack);
		codec.write(entityequipment);
	}

}
