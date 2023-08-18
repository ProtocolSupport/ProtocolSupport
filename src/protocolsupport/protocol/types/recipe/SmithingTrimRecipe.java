package protocolsupport.protocol.types.recipe;

import io.netty.buffer.ByteBuf;

public class SmithingTrimRecipe extends Recipe {

	protected final RecipeIngredient template;
	protected final RecipeIngredient base;
	protected final RecipeIngredient addition;

	public SmithingTrimRecipe(String id, ByteBuf data) {
		super(id, RecipeType.SMITHING_TRIM);

		template = new RecipeIngredient(data);
		base = new RecipeIngredient(data);
		addition = new RecipeIngredient(data);
	}

	public RecipeIngredient getTemplate() {
		return template;
	}

	public RecipeIngredient getBase() {
		return base;
	}

	public RecipeIngredient getAddition() {
		return addition;
	}

}
