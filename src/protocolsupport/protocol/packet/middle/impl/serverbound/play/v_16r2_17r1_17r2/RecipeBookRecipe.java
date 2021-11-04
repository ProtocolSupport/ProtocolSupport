package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_16r2_17r1_17r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleRecipeBookRecipe;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV16r2;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV17r2;

public class RecipeBookRecipe extends MiddleRecipeBookRecipe implements
IServerboundMiddlePacketV16r2,
IServerboundMiddlePacketV17r1,
IServerboundMiddlePacketV17r2 {

	public RecipeBookRecipe(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		recipeId = StringCodec.readVarIntUTF8String(clientdata, Short.MAX_VALUE);
	}

}
