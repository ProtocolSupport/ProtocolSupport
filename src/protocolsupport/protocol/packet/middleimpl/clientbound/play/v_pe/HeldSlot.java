package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleHeldSlot;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEInventory.PESource;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class HeldSlot extends MiddleHeldSlot {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.PLAYER_HOTBAR, connection.getVersion());
		VarNumberSerializer.writeVarInt(serializer, slot-36);
		serializer.writeByte(PESource.POCKET_INVENTORY);
		int[] linkedSlots = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};
		VarNumberSerializer.writeVarInt(serializer, linkedSlots.length);
		for(int i = 0; i < linkedSlots.length; i++) {
			VarNumberSerializer.writeVarInt(serializer, linkedSlots[i]);
		}
		serializer.writeBoolean(true); //Set held slot!
		return RecyclableSingletonList.create(serializer);
	}

}
