package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityEquipment;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.AttributesCache;
import protocolsupport.protocol.types.NetworkItemStack;

public class EntityEquipment extends MiddleEntityEquipment {

	protected final AttributesCache clientCache = cache.getAttributesCache();

	public EntityEquipment(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		codec.write(create(version, clientCache.getLocale(), entityId, slot, itemstack));
	}

	public static final MiddleEntityEquipment.Slot[] SUPPORTED_SLOTS = MiddleEntityEquipment.Slot.values();

	public static ClientBoundPacketData create(
		ProtocolVersion version, String locale,
		int entityId, Slot slot, NetworkItemStack itemstack
	) {
		ClientBoundPacketData entityequipment = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_EQUIPMENT);
		VarNumberSerializer.writeVarInt(entityequipment, entityId);
		MiscSerializer.writeVarIntEnum(entityequipment, slot);
		ItemStackSerializer.writeItemStack(entityequipment, version, locale, itemstack);
		return entityequipment;
	}

}
