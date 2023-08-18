package protocolsupport.protocol.types.recipe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.types.NetworkItemStack;

public class SmithingTransformRecipe extends Recipe {

	protected final RecipeIngredient template;
	protected final RecipeIngredient base;
	protected final RecipeIngredient addition;
	protected final NetworkItemStack result;

	public SmithingTransformRecipe(String id, ByteBuf data) {
		super(id, RecipeType.SMITHING_TRANSFORM);

		template = new RecipeIngredient(data);
		base = new RecipeIngredient(data);
		addition = new RecipeIngredient(data);
		result = ItemStackCodec.readItemStack(data);
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

	public NetworkItemStack getResult() {
		return result;
	}

}
