package protocolsupport.protocol.types.recipe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.types.NetworkItemStack;

public class SmeltingRecipe extends Recipe {
	protected final String group;
	protected final RecipeCategory category;
	protected final RecipeIngredient ingredient;
	protected final NetworkItemStack result;
	protected final float exp;
	protected final int time;

	public SmeltingRecipe(String id, RecipeType type, ByteBuf data) {
		super(id, type);

		group = StringCodec.readVarIntUTF8String(data);
		category = MiscDataCodec.readVarIntEnum(data, RecipeCategory.CONSTANT_LOOKUP);
		ingredient = new RecipeIngredient(data);
		result = ItemStackCodec.readItemStack(data);
		exp = data.readFloat();
		time = VarNumberCodec.readVarInt(data);
	}

	public String getGroup() {
		return group;
	}

	public RecipeCategory getCategory() {
		return category;
	}

	public RecipeIngredient getIngredient() {
		return ingredient;
	}

	public NetworkItemStack getResult() {
		return result;
	}

	public float getExp() {
		return exp;
	}

	public int getTime() {
		return time;
	}

}