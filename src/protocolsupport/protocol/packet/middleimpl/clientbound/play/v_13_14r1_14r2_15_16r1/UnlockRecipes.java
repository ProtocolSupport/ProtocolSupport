package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13_14r1_14r2_15_16r1;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleUnlockRecipes;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.MiscSerializer;

public class UnlockRecipes extends MiddleUnlockRecipes {

	public UnlockRecipes(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData unlockrecipes = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_UNLOCK_RECIPES);
		MiscSerializer.writeVarIntEnum(unlockrecipes, action);
		unlockrecipes.writeBoolean(craftRecipeBookOpen);
		unlockrecipes.writeBoolean(craftRecipeBookFiltering);
		unlockrecipes.writeBoolean(smeltingRecipeBookOpen);
		unlockrecipes.writeBoolean(smeltingRecipeBookFiltering);
		ArraySerializer.writeVarIntVarIntUTF8StringArray(unlockrecipes, recipes1);
		if (action == Action.INIT) {
			ArraySerializer.writeVarIntVarIntUTF8StringArray(unlockrecipes, recipes2);
		}
		codec.write(unlockrecipes);
	}

}
