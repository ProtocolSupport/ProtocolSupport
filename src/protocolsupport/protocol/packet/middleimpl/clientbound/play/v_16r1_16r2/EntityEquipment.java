package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_16r1_16r2;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityEquipment;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class EntityEquipment extends MiddleEntityEquipment {

	public EntityEquipment(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		String locale = clientCache.getLocale();

		ClientBoundPacketData entityequipment = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_EQUIPMENT);
		VarNumberSerializer.writeVarInt(entityequipment, entityId);
		int lastEntryIndex = entries.size() - 1;
		for (int entryIndex = 0; entryIndex <= lastEntryIndex; entryIndex++) {
			Entry entry = entries.get(entryIndex);
			entityequipment.writeByte(entry.getSlot().ordinal() | (entryIndex != lastEntryIndex ? Byte.MIN_VALUE : 0));
			ItemStackSerializer.writeItemStack(entityequipment, version, locale, entry.getItemStack());
		}
		codec.writeClientbound(entityequipment);
	}

}
