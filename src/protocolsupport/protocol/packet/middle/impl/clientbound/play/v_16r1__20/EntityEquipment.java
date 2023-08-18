package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_16r1__20;

import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleEntityEquipment;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV16r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV16r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV18;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV20;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class EntityEquipment extends MiddleEntityEquipment implements
IClientboundMiddlePacketV16r1,
IClientboundMiddlePacketV16r2,
IClientboundMiddlePacketV17r1,
IClientboundMiddlePacketV17r2,
IClientboundMiddlePacketV18,
IClientboundMiddlePacketV20 {

	public EntityEquipment(IMiddlePacketInit init) {
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
		io.writeClientbound(entityequipment);
	}

}
