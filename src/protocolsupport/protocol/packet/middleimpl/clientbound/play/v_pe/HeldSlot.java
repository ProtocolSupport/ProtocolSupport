package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleHeldSlot;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class HeldSlot extends MiddleHeldSlot {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		cache.getPEInventoryCache().setSelectedSlot(slot);
		return RecyclableSingletonList.create(EntityEquipment.createUpdateHand(connection.getVersion(), 
			cache.getAttributesCache().getLocale(),
			cache.getWatchedEntityCache().getSelfPlayerEntityId(), 
			cache.getWatchedEntityCache().getSelfPlayer().getDataCache().getHand(), 
			slot, true
		));
	}

}
