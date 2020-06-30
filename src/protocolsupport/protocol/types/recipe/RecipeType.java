package protocolsupport.protocol.types.recipe;

import java.util.Map;

import org.bukkit.NamespacedKey;

import io.netty.buffer.ByteBuf;
import protocolsupport.utils.CollectionsUtils;

public enum RecipeType {

	CRAFTING_SHAPELESS {
		@Override
		public Recipe read(String id, ByteBuf data) {
			return new ShapelessRecipe(id, data);
		}
	},
	CRAFTING_SHAPED {
		@Override
		public Recipe read(String id, ByteBuf data) {
			return new ShapedRecipe(id, data);
		}
	},
	CRAFTING_SPECIAL_ARMORDYE,
	CRAFTING_SPECIAL_BOOKCLONING,
	CRAFTING_SPECIAL_MAPCLONING,
	CRAFTING_SPECIAL_MAPEXTENDING,
	CRAFTING_SPECIAL_FIREWORK_ROCKET,
	CRAFTING_SPECIAL_FIREWORK_STAR,
	CRAFTING_SPECIAL_FIREWORK_STAR_FADE,
	CRAFTING_SPECIAL_REPAIRITEM,
	CRAFTING_SPECIAL_TIPPEDARROW,
	CRAFTING_SPECIAL_BANNERDUPLICATE,
	CRAFTING_SPECIAL_BANNERADDPATTERN,
	CRAFTING_SPECIAL_SHIELDDECORATION,
	CRAFTING_SPECIAL_SHULKERBOXCOLORING,
	CRAFTING_SPECIAL_SUSPICIOUSSTEW,
	SMELTING {
		@Override
		public Recipe read(String id, ByteBuf data) {
			return new SmeltingRecipe(id, RecipeType.SMELTING, data);
		}
	},
	BLASTING {
		@Override
		public Recipe read(String id, ByteBuf data) {
			return new SmeltingRecipe(id, RecipeType.BLASTING, data);
		}
	},
	SMOKING {
		@Override
		public Recipe read(String id, ByteBuf data) {
			return new SmeltingRecipe(id, RecipeType.SMOKING, data);
		}
	},
	CAMPFIRE_COOKING {
		@Override
		public Recipe read(String id, ByteBuf data) {
			return new SmeltingRecipe(id, RecipeType.CAMPFIRE_COOKING, data);
		}
	},
	STONECUTTING {
		@Override
		public Recipe read(String id, ByteBuf data) {
			return new StonecuttingRecipe(id, data);
		}
	},
	SMITHING {
		@Override
		public Recipe read(String id, ByteBuf data) {
			return new SmithingRecipe(id, data);
		}
	};

	private static final Map<String, RecipeType> byInternalName = CollectionsUtils.makeEnumMappingMap(RecipeType.class, RecipeType::getInternalName);

	private final String internalName = NamespacedKey.minecraft(name().toLowerCase()).toString();

	public static RecipeType getByInternalName(String name) {
		RecipeType recipeType = byInternalName.get(name);
		if (recipeType == null) {
			throw new IllegalArgumentException(name + " is no valid recipe type");
		}
		return recipeType;
	}

	public String getInternalName() {
		return internalName;
	}

	public Recipe read(String id, ByteBuf data) {
		return new Recipe(id, this);
	}

}