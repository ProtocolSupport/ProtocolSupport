package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8.AbstractNoOffhandEntityEquipment;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.types.NetworkItemStack;

public class EntityEquipment extends AbstractNoOffhandEntityEquipment {

	public EntityEquipment(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void writeToClient0(Slot slot, NetworkItemStack itemstack) {
		codec.writeClientbound(create(version, clientCache.getLocale(), entityId, slot, itemstack));
	}

	public static ClientBoundPacketData create(
		ProtocolVersion version, String locale,
		int entityId, Slot slot, NetworkItemStack itemstack
	) {
		ClientBoundPacketData entityequipment = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_EQUIPMENT);
		entityequipment.writeInt(entityId);
		entityequipment.writeShort(getSlotId(slot));
		ItemStackSerializer.writeItemStack(entityequipment, version, locale, itemstack);
		return entityequipment;
	}

}
