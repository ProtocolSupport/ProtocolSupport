package protocolsupport.protocol.types.recipe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.types.NetworkItemStack;

public class StonecuttingRecipe extends Recipe {

	protected final String group;
	protected final RecipeIngredient ingredient;
	protected final NetworkItemStack result;

	public StonecuttingRecipe(String id, ByteBuf data) {
		super(id, RecipeType.STONECUTTING);
		group = StringSerializer.readVarIntUTF8String(data);
		ingredient = new RecipeIngredient(data);
		result = ItemStackSerializer.readItemStack(data);
	}

	public String getGroup() {
		return group;
	}

	public RecipeIngredient getIngredient() {
		return ingredient;
	}

	public NetworkItemStack getResult() {
		return result;
	}

}