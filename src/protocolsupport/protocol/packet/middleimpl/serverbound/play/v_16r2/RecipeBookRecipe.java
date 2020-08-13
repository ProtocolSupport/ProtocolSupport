package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_16r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleRecipeBookRecipe;
import protocolsupport.protocol.serializer.StringSerializer;

public class RecipeBookRecipe extends MiddleRecipeBookRecipe {

	public RecipeBookRecipe(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void readClientData(ByteBuf clientdata) {
		recipeId = StringSerializer.readVarIntUTF8String(clientdata);
	}

}
