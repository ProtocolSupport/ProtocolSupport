package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13_14r1_14r2_15_16r1_16r2_17r1_17r2;

import java.text.MessageFormat;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleDeclareRecipes;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.recipe.Recipe;
import protocolsupport.protocol.types.recipe.RecipeIngredient;
import protocolsupport.protocol.types.recipe.RecipeType;
import protocolsupport.protocol.types.recipe.ShapedRecipe;
import protocolsupport.protocol.types.recipe.ShapelessRecipe;
import protocolsupport.protocol.types.recipe.SmeltingRecipe;
import protocolsupport.protocol.types.recipe.SmithingRecipe;
import protocolsupport.protocol.types.recipe.StonecuttingRecipe;
import protocolsupport.protocol.utils.NamespacedKeyUtils;
import protocolsupport.protocol.utils.i18n.I18NData;

public abstract class AbstractDeclareRecipes extends MiddleDeclareRecipes {

	protected AbstractDeclareRecipes(MiddlePacketInit init) {
		super(init);
	}

	protected static class RecipeWriter<T extends Recipe> {

		public static final RecipeWriter<Recipe> NOOP = new RecipeWriter<>() {
			@Override
			public boolean writeRecipe(ByteBuf to, ProtocolVersion version, Recipe recipe) {
				return false;
			}
		};

		public static final RecipeWriter<Recipe> SIMPLE = new RecipeWriter<>();

		public static final RecipeWriter<ShapelessRecipe> SHAPELESS = new RecipeWriter<>() {
			@Override
			protected void writeRecipeData(ByteBuf to, ProtocolVersion version, ShapelessRecipe recipe) {
				StringCodec.writeVarIntUTF8String(to, recipe.getGroup());
				ArrayCodec.writeVarIntTArray(to, recipe.getIngredients(), (lTo, ingredient) -> writeIngredient(lTo, version, ingredient));
				ItemStackCodec.writeItemStack(to, version, I18NData.DEFAULT_LOCALE, recipe.getResult());
			}
		};

		public static final RecipeWriter<ShapedRecipe> SHAPED = new RecipeWriter<>() {
			@Override
			protected void writeRecipeData(ByteBuf to, ProtocolVersion version, ShapedRecipe recipe) {
				VarNumberCodec.writeVarInt(to, recipe.getWidth());
				VarNumberCodec.writeVarInt(to, recipe.getHeight());
				StringCodec.writeVarIntUTF8String(to, recipe.getGroup());
				for (RecipeIngredient ingredient : recipe.getIngredients()) {
					writeIngredient(to, version, ingredient);
				}
				ItemStackCodec.writeItemStack(to, version, I18NData.DEFAULT_LOCALE, recipe.getResult());
			}
		};

		public static final RecipeWriter<SmeltingRecipe> SMELTING = new RecipeWriter<>() {
			@Override
			protected void writeRecipeData(ByteBuf to, ProtocolVersion version, SmeltingRecipe recipe) {
				StringCodec.writeVarIntUTF8String(to, recipe.getGroup());
				writeIngredient(to, version, recipe.getIngredient());
				ItemStackCodec.writeItemStack(to, version, I18NData.DEFAULT_LOCALE, recipe.getResult());
				to.writeFloat(recipe.getExp());
				VarNumberCodec.writeVarInt(to, recipe.getTime());
			}
		};

		public static final RecipeWriter<StonecuttingRecipe> STONECUTTING = new RecipeWriter<>() {
			@Override
			protected void writeRecipeData(ByteBuf to, ProtocolVersion version, StonecuttingRecipe recipe) {
				StringCodec.writeVarIntUTF8String(to, recipe.getGroup());
				writeIngredient(to, version, recipe.getIngredient());
				ItemStackCodec.writeItemStack(to, version, I18NData.DEFAULT_LOCALE, recipe.getResult());
			}
		};

		public static final RecipeWriter<SmithingRecipe> SMITHING = new RecipeWriter<>() {
			@Override
			protected void writeRecipeData(ByteBuf to, ProtocolVersion version, SmithingRecipe recipe) {
				writeIngredient(to, version, recipe.getBase());
				writeIngredient(to, version, recipe.getAddition());
				ItemStackCodec.writeItemStack(to, version, I18NData.DEFAULT_LOCALE, recipe.getResult());
			}
		};

		public boolean writeRecipe(ByteBuf to, ProtocolVersion version, T recipe) {
			if (version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_14)) {
				StringCodec.writeVarIntUTF8String(to, recipe.getType().getInternalName());
				StringCodec.writeVarIntUTF8String(to, recipe.getId());
			} else {
				StringCodec.writeVarIntUTF8String(to, recipe.getId());
				StringCodec.writeVarIntUTF8String(to, NamespacedKeyUtils.fromString(recipe.getType().getInternalName()).getKey());
			}
			writeRecipeData(to, version, recipe);
			return true;
		}

		protected void writeRecipeData(ByteBuf to, ProtocolVersion version, T recipe) {
		}

		protected static void writeIngredient(ByteBuf to, ProtocolVersion version, RecipeIngredient ingredient) {
			NetworkItemStack[] possibleStacks = ingredient.getPossibleItemStacks();
			VarNumberCodec.writeVarInt(to, possibleStacks.length);
			for (int i = 0; i < possibleStacks.length; i++) {
				ItemStackCodec.writeItemStack(to, version, I18NData.DEFAULT_LOCALE, possibleStacks[i]);
			}
		}

	}

	@Override
	protected void write() {
		ClientBoundPacketData declarerecipes = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_DECLARE_RECIPES);
		MiscDataCodec.writeVarIntCountPrefixedType(declarerecipes, recipes, (recipesTo, recipes) -> {
			int writtenRecipeCount = 0;
			for (Recipe recipe : recipes) {
				RecipeWriter<Recipe> writer = getRecipeWriter(recipe.getType());
				if (writer == null) {
					throw new IllegalArgumentException(MessageFormat.format("Missing recipe writer for recipe type {0}", recipe.getType()));
				}
				if (writer.writeRecipe(recipesTo, version, recipe)) {
					writtenRecipeCount++;
				}
			}
			return writtenRecipeCount;
		});
		codec.writeClientbound(declarerecipes);
	}

	protected abstract RecipeWriter<Recipe> getRecipeWriter(RecipeType recipeType);

}
