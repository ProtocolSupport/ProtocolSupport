package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13_14r1_14r2_15_16r1_16r2_17r1_17r2;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCraftRecipeConfirm;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class CraftRecipeConfirm extends MiddleCraftRecipeConfirm {

	public CraftRecipeConfirm(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData craftrecipeconfirm = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_CRAFT_RECIPE_CONFIRM);
		craftrecipeconfirm.writeByte(windowId);
		StringCodec.writeVarIntUTF8String(craftrecipeconfirm, recipeId);
		codec.writeClientbound(craftrecipeconfirm);
	}

}
