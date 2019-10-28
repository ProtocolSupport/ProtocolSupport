package protocolsupport.protocol.types.recipe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.NetworkItemStack;

public class ShapedRecipe extends Recipe {
	protected final String group;
	protected final int width;
	protected final int height;
	protected final RecipeIngredient[] ingredients;
	protected final NetworkItemStack result;

	public ShapedRecipe(String id, ByteBuf data) {
		super(id, RecipeType.CRAFTING_SHAPED);

		width = VarNumberSerializer.readVarInt(data);
		height = VarNumberSerializer.readVarInt(data);
		group = StringSerializer.readVarIntUTF8String(data);
		int ingredientCount = width * height;
		ingredients = new RecipeIngredient[ingredientCount];
		for (int j = 0; j < ingredientCount; j++) {
			ingredients[j] = new RecipeIngredient(data);
		}
		result = ItemStackSerializer.readItemStack(data);
	}

	public String getGroup() {
		return group;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public RecipeIngredient[] getIngredients() {
		return ingredients;
	}

	public NetworkItemStack getResult() {
		return result;
	}
}