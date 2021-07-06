package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_16r1_16r2_17r1_17r2;

import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityEquipment;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class EntityEquipment extends MiddleEntityEquipment {

	public EntityEquipment(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		String locale = clientCache.getLocale();

		ClientBoundPacketData entityequipment = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ENTITY_EQUIPMENT);
		VarNumberCodec.writeVarInt(entityequipment, entityId);
		int lastEntryIndex = entries.size() - 1;
		for (int entryIndex = 0; entryIndex <= lastEntryIndex; entryIndex++) {
			Entry entry = entries.get(entryIndex);
			entityequipment.writeByte(entry.getSlot().ordinal() | (entryIndex != lastEntryIndex ? Byte.MIN_VALUE : 0));
			ItemStackCodec.writeItemStack(entityequipment, version, locale, entry.getItemStack());
		}
		codec.writeClientbound(entityequipment);
	}

}
