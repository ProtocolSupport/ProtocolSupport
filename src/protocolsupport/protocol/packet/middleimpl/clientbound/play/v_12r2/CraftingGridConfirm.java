package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_12r2;

import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCraftingGridConfirm;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class CraftingGridConfirm extends MiddleCraftingGridConfirm {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData creator = ClientBoundPacketData.create(ClientBoundPacket.PLAY_CRAFTING_GRID_CONFIRM);
		creator.writeByte(windowId);
		VarNumberSerializer.writeVarInt(creator, recipeId);
		return RecyclableSingletonList.create(creator);
	}

}
