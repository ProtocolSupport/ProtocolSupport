package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleHeldSlot;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class HeldSlot extends MiddleHeldSlot {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		cache.getPEInventoryCache().setSelectedSlot(slot);
		return RecyclableSingletonList.create(create(slot));
	}

	public static ClientBoundPacketData create(int slot) {
		return create(slot, 0, true);
	}

	public static ClientBoundPacketData create(int slot, int containerType, boolean select) {
		ClientBoundPacketData data = ClientBoundPacketData.create(PEPacketIDs.PLAYER_HOTBAR);
		VarNumberSerializer.writeVarInt(data, slot);
		data.writeByte(containerType);
		data.writeBoolean(select);
		return data;
	}

}
