package protocolsupport.protocol.types.recipe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.MiscDataCodec;

public class SpecialRecipe extends Recipe {

	protected final RecipeCategory category;

	public SpecialRecipe(String id, RecipeType type, ByteBuf data) {
		super(id, type);

		category = MiscDataCodec.readVarIntEnum(data, RecipeCategory.CONSTANT_LOOKUP);
	}

	public RecipeCategory getCategory() {
		return category;
	}

}
