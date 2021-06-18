package protocolsupport.protocol.types.recipe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.types.NetworkItemStack;

public class SmithingRecipe extends Recipe {

	protected final RecipeIngredient base;
	protected final RecipeIngredient addition;
	protected final NetworkItemStack result;

	public SmithingRecipe(String id, ByteBuf data) {
		super(id, RecipeType.SMITHING);

		base = new RecipeIngredient(data);
		addition = new RecipeIngredient(data);
		result = ItemStackCodec.readItemStack(data);
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
