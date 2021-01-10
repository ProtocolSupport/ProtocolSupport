package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.recipe.Recipe;
import protocolsupport.protocol.types.recipe.RecipeType;

public abstract class MiddleDeclareRecipes extends ClientBoundMiddlePacket {

	public MiddleDeclareRecipes(MiddlePacketInit init) {
		super(init);
	}

	protected Recipe[] recipes;

	@Override
	protected void decode(ByteBuf serverdata) {
		int count = VarNumberSerializer.readVarInt(serverdata);
		recipes = new Recipe[count];
		for (int i = 0; i < count; i++) {
			RecipeType type = RecipeType.getByInternalName(StringSerializer.readVarIntUTF8String(serverdata));
			recipes[i] = type.read(StringSerializer.readVarIntUTF8String(serverdata), serverdata);
		}
	}

	@Override
	protected void cleanup() {
		recipes = null;
	}

}
