package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13_14;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleUnlockRecipes;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class UnlockRecipes extends MiddleUnlockRecipes {

	public UnlockRecipes(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_UNLOCK_RECIPES);
		MiscSerializer.writeVarIntEnum(serializer, action);
		serializer.writeBoolean(craftRecipeBookOpen);
		serializer.writeBoolean(craftRecipeBookFiltering);
		serializer.writeBoolean(smeltingRecipeBookOpen);
		serializer.writeBoolean(smeltingRecipeBookFiltering);
		ArraySerializer.writeVarIntStringArray(serializer, version, recipes1);
		if (action == Action.INIT) {
			ArraySerializer.writeVarIntStringArray(serializer, version, recipes2);
		}
		return RecyclableSingletonList.create(serializer);
	}

}
