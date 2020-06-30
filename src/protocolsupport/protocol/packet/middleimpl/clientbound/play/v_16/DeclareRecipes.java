package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_16;

import java.util.EnumMap;
import java.util.Map;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13_14r1_14r2_15_16.AbstractDeclareRecipes;
import protocolsupport.protocol.types.recipe.Recipe;
import protocolsupport.protocol.types.recipe.RecipeType;

public class DeclareRecipes extends AbstractDeclareRecipes {

	public DeclareRecipes(ConnectionImpl connection) {
		super(connection);
	}

	protected static final Map<RecipeType, RecipeWriter<Recipe>> recipeWriter = new EnumMap<>(RecipeType.class);

	@SuppressWarnings("unchecked")
	protected static <T extends Recipe> void registerWriter(RecipeType type, RecipeWriter<T> writer) {
		recipeWriter.put(type, (RecipeWriter<Recipe>) writer);
	}

	static {
		registerWriter(RecipeType.CRAFTING_SPECIAL_ARMORDYE, RecipeWriter.SIMPLE);
		registerWriter(RecipeType.CRAFTING_SPECIAL_BOOKCLONING, RecipeWriter.SIMPLE);
		registerWriter(RecipeType.CRAFTING_SPECIAL_MAPCLONING, RecipeWriter.SIMPLE);
		registerWriter(RecipeType.CRAFTING_SPECIAL_MAPEXTENDING, RecipeWriter.SIMPLE);
		registerWriter(RecipeType.CRAFTING_SPECIAL_FIREWORK_ROCKET, RecipeWriter.SIMPLE);
		registerWriter(RecipeType.CRAFTING_SPECIAL_FIREWORK_STAR, RecipeWriter.SIMPLE);
		registerWriter(RecipeType.CRAFTING_SPECIAL_FIREWORK_STAR_FADE, RecipeWriter.SIMPLE);
		registerWriter(RecipeType.CRAFTING_SPECIAL_REPAIRITEM, RecipeWriter.SIMPLE);
		registerWriter(RecipeType.CRAFTING_SPECIAL_TIPPEDARROW, RecipeWriter.SIMPLE);
		registerWriter(RecipeType.CRAFTING_SPECIAL_BANNERDUPLICATE, RecipeWriter.SIMPLE);
		registerWriter(RecipeType.CRAFTING_SPECIAL_BANNERADDPATTERN, RecipeWriter.SIMPLE);
		registerWriter(RecipeType.CRAFTING_SPECIAL_SHIELDDECORATION, RecipeWriter.SIMPLE);
		registerWriter(RecipeType.CRAFTING_SPECIAL_SHULKERBOXCOLORING, RecipeWriter.SIMPLE);
		registerWriter(RecipeType.CRAFTING_SPECIAL_SUSPICIOUSSTEW, RecipeWriter.SIMPLE);
		registerWriter(RecipeType.CRAFTING_SHAPELESS, RecipeWriter.SHAPELESS);
		registerWriter(RecipeType.CRAFTING_SHAPED, RecipeWriter.SHAPED);
		registerWriter(RecipeType.SMELTING, RecipeWriter.SMELTING);
		registerWriter(RecipeType.BLASTING,  RecipeWriter.SMELTING);
		registerWriter(RecipeType.SMOKING,  RecipeWriter.SMELTING);
		registerWriter(RecipeType.CAMPFIRE_COOKING,  RecipeWriter.SMELTING);
		registerWriter(RecipeType.STONECUTTING, RecipeWriter.STONECUTTING);
		registerWriter(RecipeType.SMITHING, RecipeWriter.SMITHING);
	}

	@Override
	protected RecipeWriter<Recipe> getRecipeWriter(RecipeType recipeType) {
		return recipeWriter.get(recipeType);
	}

}
