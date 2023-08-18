package protocolsupport.protocol.types.recipe;

import java.util.Locale;
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
	CRAFTING_SPECIAL_ARMORDYE {
		@Override
		public Recipe read(String id, ByteBuf data) {
			return new SpecialRecipe(id, RecipeType.CRAFTING_SPECIAL_ARMORDYE, data);
		}
	},
	CRAFTING_SPECIAL_BOOKCLONING {
		@Override
		public Recipe read(String id, ByteBuf data) {
			return new SpecialRecipe(id, RecipeType.CRAFTING_SPECIAL_BOOKCLONING, data);
		}
	},
	CRAFTING_SPECIAL_MAPCLONING {
		@Override
		public Recipe read(String id, ByteBuf data) {
			return new SpecialRecipe(id, RecipeType.CRAFTING_SPECIAL_MAPCLONING, data);
		}
	},
	CRAFTING_SPECIAL_MAPEXTENDING {
		@Override
		public Recipe read(String id, ByteBuf data) {
			return new SpecialRecipe(id, RecipeType.CRAFTING_SPECIAL_MAPEXTENDING, data);
		}
	},
	CRAFTING_SPECIAL_FIREWORK_ROCKET {
		@Override
		public Recipe read(String id, ByteBuf data) {
			return new SpecialRecipe(id, RecipeType.CRAFTING_SPECIAL_FIREWORK_ROCKET, data);
		}
	},
	CRAFTING_SPECIAL_FIREWORK_STAR {
		@Override
		public Recipe read(String id, ByteBuf data) {
			return new SpecialRecipe(id, RecipeType.CRAFTING_SPECIAL_FIREWORK_STAR, data);
		}
	},
	CRAFTING_SPECIAL_FIREWORK_STAR_FADE {
		@Override
		public Recipe read(String id, ByteBuf data) {
			return new SpecialRecipe(id, RecipeType.CRAFTING_SPECIAL_FIREWORK_STAR_FADE, data);
		}
	},
	CRAFTING_SPECIAL_REPAIRITEM {
		@Override
		public Recipe read(String id, ByteBuf data) {
			return new SpecialRecipe(id, RecipeType.CRAFTING_SPECIAL_REPAIRITEM, data);
		}
	},
	CRAFTING_SPECIAL_TIPPEDARROW {
		@Override
		public Recipe read(String id, ByteBuf data) {
			return new SpecialRecipe(id, RecipeType.CRAFTING_SPECIAL_TIPPEDARROW, data);
		}
	},
	CRAFTING_SPECIAL_BANNERDUPLICATE {
		@Override
		public Recipe read(String id, ByteBuf data) {
			return new SpecialRecipe(id, RecipeType.CRAFTING_SPECIAL_BANNERDUPLICATE, data);
		}
	},
	CRAFTING_SPECIAL_BANNERADDPATTERN {
		@Override
		public Recipe read(String id, ByteBuf data) {
			return new SpecialRecipe(id, RecipeType.CRAFTING_SPECIAL_BANNERADDPATTERN, data);
		}
	},
	CRAFTING_SPECIAL_SHIELDDECORATION {
		@Override
		public Recipe read(String id, ByteBuf data) {
			return new SpecialRecipe(id, RecipeType.CRAFTING_SPECIAL_SHIELDDECORATION, data);
		}
	},
	CRAFTING_SPECIAL_SHULKERBOXCOLORING {
		@Override
		public Recipe read(String id, ByteBuf data) {
			return new SpecialRecipe(id, RecipeType.CRAFTING_SPECIAL_SHULKERBOXCOLORING, data);
		}
	},
	CRAFTING_SPECIAL_SUSPICIOUSSTEW {
		@Override
		public Recipe read(String id, ByteBuf data) {
			return new SpecialRecipe(id, RecipeType.CRAFTING_SPECIAL_SUSPICIOUSSTEW, data);
		}
	},
	CRAFTING_DECORATED_POT {
		@Override
		public Recipe read(String id, ByteBuf data) {
			return new SpecialRecipe(id, RecipeType.CRAFTING_DECORATED_POT, data);
		}
	},
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
	SMITHING_TRANSFORM {
		@Override
		public Recipe read(String id, ByteBuf data) {
			return new SmithingTransformRecipe(id, data);
		}
	},
	SMITHING_TRIM {
		@Override
		public Recipe read(String id, ByteBuf data) {
			return new SmithingTrimRecipe(id, data);
		}
	};

	private static final Map<String, RecipeType> byInternalName = CollectionsUtils.makeEnumMappingMap(RecipeType.class, RecipeType::getInternalName);

	private final String internalName = NamespacedKey.minecraft(name().toLowerCase(Locale.ENGLISH)).toString();

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