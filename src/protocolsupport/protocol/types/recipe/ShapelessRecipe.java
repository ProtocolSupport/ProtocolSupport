package protocolsupport.protocol.types.recipe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.NetworkItemStack;

public class ShapelessRecipe extends Recipe {
	protected final String group;
	protected final RecipeIngredient[] ingredients;
	protected final NetworkItemStack result;

	public ShapelessRecipe(String id, ByteBuf data) {
		super(id, RecipeType.CRAFTING_SHAPELESS);

		group = StringSerializer.readVarIntUTF8String(data);
		int ingredientCount = VarNumberSerializer.readVarInt(data);
		ingredients = new RecipeIngredient[ingredientCount];
		for (int j = 0; j < ingredientCount; j++) {
			ingredients[j] = new RecipeIngredient(data);
		}
		result = ItemStackSerializer.readItemStack(data);
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