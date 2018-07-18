package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_12r1_12r2;

import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleUnlockRecipes;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class UnlockRecipes extends MiddleUnlockRecipes {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_UNLOCK_RECIPES);
		MiscSerializer.writeVarIntEnum(serializer, action);
		serializer.writeBoolean(craftRecipeBookOpen);
		serializer.writeBoolean(craftRecipeBookFiltering);
		ArraySerializer.writeVarIntVarIntArray(serializer, recipes1);
		if (action == Action.INIT) {
			ArraySerializer.writeVarIntVarIntArray(serializer, recipes2);
		}
		return RecyclableSingletonList.create(serializer);
	}

}
