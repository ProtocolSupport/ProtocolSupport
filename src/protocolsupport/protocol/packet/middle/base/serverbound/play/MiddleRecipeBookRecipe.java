package protocolsupport.protocol.packet.middle.base.serverbound.play;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;

public abstract class MiddleRecipeBookRecipe extends ServerBoundMiddlePacket {

	protected MiddleRecipeBookRecipe(IMiddlePacketInit init) {
		super(init);
	}

	protected String recipeId;

	@Override
	protected void write() {
		io.writeServerbound(create(recipeId));
	}

	public static ServerBoundPacketData create(String recipeId) {
		ServerBoundPacketData recipebookrecipe = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_RECIPE_BOOK_RECIPE);
		StringCodec.writeVarIntUTF8String(recipebookrecipe, recipeId);
		return recipebookrecipe;
	}

}
