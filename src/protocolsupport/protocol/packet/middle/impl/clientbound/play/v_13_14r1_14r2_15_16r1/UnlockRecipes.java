package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_13_14r1_14r2_15_16r1;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleUnlockRecipes;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV13;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV14r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV14r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV15;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV16r1;

public class UnlockRecipes extends MiddleUnlockRecipes implements
IClientboundMiddlePacketV13,
IClientboundMiddlePacketV14r1,
IClientboundMiddlePacketV14r2,
IClientboundMiddlePacketV15,
IClientboundMiddlePacketV16r1 {

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
		ArrayCodec.writeVarIntVarIntUTF8StringArray(unlockrecipes, recipes1);
		if (action == Action.INIT) {
			ArrayCodec.writeVarIntVarIntUTF8StringArray(unlockrecipes, recipes2);
		}
		io.writeClientbound(unlockrecipes);
	}

}
