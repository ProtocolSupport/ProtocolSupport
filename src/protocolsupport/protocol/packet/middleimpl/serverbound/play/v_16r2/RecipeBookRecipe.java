package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_16r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleRecipeBookRecipe;

public class RecipeBookRecipe extends MiddleRecipeBookRecipe {

	public RecipeBookRecipe(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		recipeId = StringCodec.readVarIntUTF8String(clientdata, Short.MAX_VALUE);
	}

}
