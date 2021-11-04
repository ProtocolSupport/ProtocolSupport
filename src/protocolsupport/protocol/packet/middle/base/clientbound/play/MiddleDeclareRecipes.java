package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;
import protocolsupport.protocol.types.recipe.Recipe;
import protocolsupport.protocol.types.recipe.RecipeType;

public abstract class MiddleDeclareRecipes extends ClientBoundMiddlePacket {

	protected MiddleDeclareRecipes(IMiddlePacketInit init) {
		super(init);
	}

	protected Recipe[] recipes;

	@Override
	protected void decode(ByteBuf serverdata) {
		int count = VarNumberCodec.readVarInt(serverdata);
		recipes = new Recipe[count];
		for (int i = 0; i < count; i++) {
			RecipeType type = RecipeType.getByInternalName(StringCodec.readVarIntUTF8String(serverdata));
			recipes[i] = type.read(StringCodec.readVarIntUTF8String(serverdata), serverdata);
		}
	}

	@Override
	protected void cleanup() {
		recipes = null;
	}

}
