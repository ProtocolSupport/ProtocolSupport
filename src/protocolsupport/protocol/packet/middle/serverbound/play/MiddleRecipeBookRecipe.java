package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;

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
		ServerBoundPacketData recipebookrecipe = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_RECIPE_BOOK_RECIPE);
		StringSerializer.writeVarIntUTF8String(recipebookrecipe, recipeId);
		return recipebookrecipe;
	}

}
