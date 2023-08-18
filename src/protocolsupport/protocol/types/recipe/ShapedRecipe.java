package protocolsupport.protocol.types.recipe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.types.NetworkItemStack;

public class ShapedRecipe extends Recipe {
	protected final String group;
	protected final RecipeCategory category;
	protected final int width;
	protected final int height;
	protected final RecipeIngredient[] ingredients;
	protected final NetworkItemStack result;
	protected final boolean notify;

	public ShapedRecipe(String id, ByteBuf data) {
		super(id, RecipeType.CRAFTING_SHAPED);

		width = VarNumberCodec.readVarInt(data);
		height = VarNumberCodec.readVarInt(data);
		group = StringCodec.readVarIntUTF8String(data);
		category = MiscDataCodec.readVarIntEnum(data, RecipeCategory.CONSTANT_LOOKUP);
		int ingredientCount = width * height;
		ingredients = new RecipeIngredient[ingredientCount];
		for (int j = 0; j < ingredientCount; j++) {
			ingredients[j] = new RecipeIngredient(data);
		}
		result = ItemStackCodec.readItemStack(data);
		notify = data.readBoolean();
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

	public RecipeCategory getCategory() {
		return category;
	}

	public NetworkItemStack getResult() {
		return result;
	}

	public boolean shouldNotify() {
		return notify;
	}

}