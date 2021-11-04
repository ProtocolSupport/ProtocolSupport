package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_8;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8.AbstractNoOffhandEntityEquipment;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.types.NetworkItemStack;

public class EntityEquipment extends AbstractNoOffhandEntityEquipment implements IClientboundMiddlePacketV8 {

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
		VarNumberCodec.writeVarInt(entityequipment, entityId);
		entityequipment.writeShort(getSlotId(slot));
		ItemStackCodec.writeItemStack(entityequipment, version, locale, itemstack);
		return entityequipment;
	}

}
