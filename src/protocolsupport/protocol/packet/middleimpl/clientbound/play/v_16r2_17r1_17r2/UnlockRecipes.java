package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_16r2_17r1_17r2;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleUnlockRecipes;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class UnlockRecipes extends MiddleUnlockRecipes {

	public UnlockRecipes(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData unlockrecipes = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_UNLOCK_RECIPES);
		MiscDataCodec.writeVarIntEnum(unlockrecipes, action);
		unlockrecipes.writeBoolean(craftRecipeBookOpen);
		unlockrecipes.writeBoolean(craftRecipeBookFiltering);
		unlockrecipes.writeBoolean(smeltingRecipeBookOpen);
		unlockrecipes.writeBoolean(smeltingRecipeBookFiltering);
		unlockrecipes.writeBoolean(blastFurnaceRecipeBookOpen);
		unlockrecipes.writeBoolean(blastFurnaceRecipeBookFiltering);
		unlockrecipes.writeBoolean(smokerRecipeBookOpen);
		unlockrecipes.writeBoolean(smokerRecipeBookFiltering);
		ArrayCodec.writeVarIntVarIntUTF8StringArray(unlockrecipes, recipes1);
		if (action == Action.INIT) {
			ArrayCodec.writeVarIntVarIntUTF8StringArray(unlockrecipes, recipes2);
		}
		codec.writeClientbound(unlockrecipes);
	}

}
