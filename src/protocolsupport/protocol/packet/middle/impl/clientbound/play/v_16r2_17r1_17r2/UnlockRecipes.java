package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_16r2_17r1_17r2;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleUnlockRecipes;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV16r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r2;

public class UnlockRecipes extends MiddleUnlockRecipes implements
IClientboundMiddlePacketV16r2,
IClientboundMiddlePacketV17r1,
IClientboundMiddlePacketV17r2 {

	public UnlockRecipes(IMiddlePacketInit init) {
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
		io.writeClientbound(unlockrecipes);
	}

}
