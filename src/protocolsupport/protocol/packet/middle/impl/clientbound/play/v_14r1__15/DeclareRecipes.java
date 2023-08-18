package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_14r1__15;

import java.util.EnumMap;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV14r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV14r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV15;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_13__20.AbstractDeclareRecipes;
import protocolsupport.protocol.types.recipe.Recipe;
import protocolsupport.protocol.types.recipe.RecipeType;

public class DeclareRecipes extends AbstractDeclareRecipes implements
IClientboundMiddlePacketV14r1,
IClientboundMiddlePacketV14r2,
IClientboundMiddlePacketV15 {

	public DeclareRecipes(IMiddlePacketInit init) {
		super(init);
	}

	protected static final Map<RecipeType, RecipeWriter<Recipe>> recipeWriter = new EnumMap<>(RecipeType.class);

	@SuppressWarnings("unchecked")
	protected static <T extends Recipe> void registerWriter(RecipeType type, RecipeWriter<T> writer) {
		recipeWriter.put(type, (RecipeWriter<Recipe>) writer);
	}

	static {
		registerWriter(RecipeType.CRAFTING_SPECIAL_ARMORDYE, RecipeWriter.SPECIAL);
		registerWriter(RecipeType.CRAFTING_SPECIAL_BOOKCLONING, RecipeWriter.SPECIAL);
		registerWriter(RecipeType.CRAFTING_SPECIAL_MAPCLONING, RecipeWriter.SPECIAL);
		registerWriter(RecipeType.CRAFTING_SPECIAL_MAPEXTENDING, RecipeWriter.SPECIAL);
		registerWriter(RecipeType.CRAFTING_SPECIAL_FIREWORK_ROCKET, RecipeWriter.SPECIAL);
		registerWriter(RecipeType.CRAFTING_SPECIAL_FIREWORK_STAR, RecipeWriter.SPECIAL);
		registerWriter(RecipeType.CRAFTING_SPECIAL_FIREWORK_STAR_FADE, RecipeWriter.SPECIAL);
		registerWriter(RecipeType.CRAFTING_SPECIAL_REPAIRITEM, new RecipeWriter<>() {
			@Override
			public boolean writeRecipe(ByteBuf to, ProtocolVersion version, Recipe recipe) {
				if (version.isBefore(ProtocolVersion.MINECRAFT_1_14_3)) {
					return false;
				}
				return super.writeRecipe(to, version, recipe);
			}
		});
		registerWriter(RecipeType.CRAFTING_SPECIAL_TIPPEDARROW, RecipeWriter.SPECIAL);
		registerWriter(RecipeType.CRAFTING_SPECIAL_BANNERDUPLICATE, RecipeWriter.SPECIAL);
		registerWriter(RecipeType.CRAFTING_SPECIAL_BANNERADDPATTERN, RecipeWriter.SPECIAL);
		registerWriter(RecipeType.CRAFTING_SPECIAL_SHIELDDECORATION, RecipeWriter.SPECIAL);
		registerWriter(RecipeType.CRAFTING_SPECIAL_SHULKERBOXCOLORING, RecipeWriter.SPECIAL);
		registerWriter(RecipeType.CRAFTING_SPECIAL_SUSPICIOUSSTEW, RecipeWriter.SPECIAL);
		registerWriter(RecipeType.CRAFTING_SHAPELESS, RecipeWriter.SHAPELESS);
		registerWriter(RecipeType.CRAFTING_SHAPED, RecipeWriter.SHAPED);
		registerWriter(RecipeType.SMELTING, RecipeWriter.SMELTING);
		registerWriter(RecipeType.BLASTING,  RecipeWriter.SMELTING);
		registerWriter(RecipeType.SMOKING,  RecipeWriter.SMELTING);
		registerWriter(RecipeType.CAMPFIRE_COOKING,  RecipeWriter.SMELTING);
		registerWriter(RecipeType.STONECUTTING, RecipeWriter.STONECUTTING);
		registerWriter(RecipeType.CRAFTING_DECORATED_POT, RecipeWriter.NOOP);
		registerWriter(RecipeType.SMITHING_TRANSFORM, RecipeWriter.NOOP);
		registerWriter(RecipeType.SMITHING_TRIM, RecipeWriter.NOOP);
	}

	@Override
	protected RecipeWriter<Recipe> getRecipeWriter(RecipeType recipeType) {
		return recipeWriter.get(recipeType);
	}

}
