package protocolsupport.protocol.types.recipe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.types.NetworkItemStack;

public class StonecuttingRecipe extends Recipe {

	protected final String group;
	protected final RecipeIngredient ingredient;
	protected final NetworkItemStack result;

	public StonecuttingRecipe(String id, ByteBuf data) {
		super(id, RecipeType.STONECUTTING);
		group = StringCodec.readVarIntUTF8String(data);
		ingredient = new RecipeIngredient(data);
		result = ItemStackCodec.readItemStack(data);
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