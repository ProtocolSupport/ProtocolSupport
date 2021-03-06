package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;

public abstract class MiddleRecipeBookRecipe extends ServerBoundMiddlePacket {

	protected MiddleRecipeBookRecipe(MiddlePacketInit init) {
		super(init);
	}

	protected String recipeId;

	@Override
	protected void write() {
		codec.writeServerbound(create(recipeId));
	}

	public static ServerBoundPacketData create(String recipeId) {
		ServerBoundPacketData recipebookrecipe = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_RECIPE_BOOK_RECIPE);
		StringCodec.writeVarIntUTF8String(recipebookrecipe, recipeId);
		return recipebookrecipe;
	}

}
