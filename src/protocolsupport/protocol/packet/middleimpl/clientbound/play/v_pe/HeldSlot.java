package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleHeldSlot;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class HeldSlot extends MiddleHeldSlot {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		cache.setSelectedSlot(slot);
		return RecyclableSingletonList.create(EntityEquipment.createUpdateHand(connection.getVersion(), cache.getLocale(),
				cache.getSelfPlayerEntityId(), cache.getWatchedSelf().getDataCache().getHand(), slot, true));
	}

}
