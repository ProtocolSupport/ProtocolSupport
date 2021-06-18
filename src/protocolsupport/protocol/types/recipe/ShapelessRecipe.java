package protocolsupport.protocol.types.recipe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.types.NetworkItemStack;

public class ShapelessRecipe extends Recipe {
	protected final String group;
	protected final RecipeIngredient[] ingredients;
	protected final NetworkItemStack result;

	public ShapelessRecipe(String id, ByteBuf data) {
		super(id, RecipeType.CRAFTING_SHAPELESS);

		group = StringCodec.readVarIntUTF8String(data);
		int ingredientCount = VarNumberCodec.readVarInt(data);
		ingredients = new RecipeIngredient[ingredientCount];
		for (int j = 0; j < ingredientCount; j++) {
			ingredients[j] = new RecipeIngredient(data);
		}
		result = ItemStackCodec.readItemStack(data);
	}

	public String getGroup() {
		return group;
	}

	public RecipeIngredient[] getIngredients() {
		return ingredients;
	}

	public NetworkItemStack getResult() {
		return result;
	}
}