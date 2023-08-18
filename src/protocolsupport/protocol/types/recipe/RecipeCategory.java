package protocolsupport.protocol.types.recipe;

import protocolsupport.protocol.utils.EnumConstantLookup;

public enum RecipeCategory {
	BUILDING, REDSTONE, EQUIPMENT, MISC;
	public static final EnumConstantLookup<RecipeCategory> CONSTANT_LOOKUP = new EnumConstantLookup<>(RecipeCategory.class);
}