package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8.AbstractNoOffhandEntityEquipment;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.types.NetworkItemStack;

public class EntityEquipment extends AbstractNoOffhandEntityEquipment implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6,
IClientboundMiddlePacketV7
{

	public EntityEquipment(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void writeToClient0(Slot slot, NetworkItemStack itemstack) {
		io.writeClientbound(create(version, clientCache.getLocale(), entityId, slot, itemstack));
	}

	public static ClientBoundPacketData create(
		ProtocolVersion version, String locale,
		int entityId, Slot slot, NetworkItemStack itemstack
	) {
		ClientBoundPacketData entityequipment = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ENTITY_EQUIPMENT);
		entityequipment.writeInt(entityId);
		entityequipment.writeShort(getSlotId(slot));
		ItemStackCodec.writeItemStack(entityequipment, version, locale, itemstack);
		return entityequipment;
	}

}
