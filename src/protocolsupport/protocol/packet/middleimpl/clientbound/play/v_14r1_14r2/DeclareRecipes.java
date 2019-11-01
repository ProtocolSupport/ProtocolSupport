package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2;

import java.util.EnumMap;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleDeclareRecipes;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.recipe.Recipe;
import protocolsupport.protocol.types.recipe.RecipeIngredient;
import protocolsupport.protocol.types.recipe.RecipeType;
import protocolsupport.protocol.types.recipe.ShapedRecipe;
import protocolsupport.protocol.types.recipe.ShapelessRecipe;
import protocolsupport.protocol.types.recipe.SmeltingRecipe;
import protocolsupport.protocol.types.recipe.StonecuttingRecipe;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class DeclareRecipes extends MiddleDeclareRecipes {

	protected static final Map<RecipeType, RecipeWriter<? extends Recipe>> recipeWriters = new EnumMap<>(RecipeType.class);
	protected static <T extends Recipe> void registerWriter(RecipeType type, RecipeWriter<T> writer) {
		recipeWriters.put(type, writer);
	}
	static {
		registerWriter(RecipeType.CRAFTING_SPECIAL_ARMORDYE, RecipeWriter.SIMPLE);
		registerWriter(RecipeType.CRAFTING_SPECIAL_BOOKCLONING, RecipeWriter.SIMPLE);
		registerWriter(RecipeType.CRAFTING_SPECIAL_MAPCLONING, RecipeWriter.SIMPLE);
		registerWriter(RecipeType.CRAFTING_SPECIAL_MAPEXTENDING, RecipeWriter.SIMPLE);
		registerWriter(RecipeType.CRAFTING_SPECIAL_FIREWORK_ROCKET, RecipeWriter.SIMPLE);
		registerWriter(RecipeType.CRAFTING_SPECIAL_FIREWORK_STAR, RecipeWriter.SIMPLE);
		registerWriter(RecipeType.CRAFTING_SPECIAL_FIREWORK_STAR_FADE, RecipeWriter.SIMPLE);
		registerWriter(RecipeType.CRAFTING_SPECIAL_REPAIRITEM, new RecipeWriter<Recipe>() {
			@Override
			public boolean writeRecipe(ByteBuf to, ProtocolVersion version, Recipe recipe) {
				if (version.isBefore(ProtocolVersion.MINECRAFT_1_14_3)) {
					return false;
				}
				return super.writeRecipe(to, version, recipe);
			}
		});
		registerWriter(RecipeType.CRAFTING_SPECIAL_TIPPEDARROW, RecipeWriter.SIMPLE);
		registerWriter(RecipeType.CRAFTING_SPECIAL_BANNERDUPLICATE, RecipeWriter.SIMPLE);
		registerWriter(RecipeType.CRAFTING_SPECIAL_BANNERADDPATTERN, RecipeWriter.SIMPLE);
		registerWriter(RecipeType.CRAFTING_SPECIAL_SHIELDDECORATION, RecipeWriter.SIMPLE);
		registerWriter(RecipeType.CRAFTING_SPECIAL_SHULKERBOXCOLORING, RecipeWriter.SIMPLE);
		registerWriter(RecipeType.CRAFTING_SPECIAL_SUSPICIOUSSTEW, RecipeWriter.SIMPLE);
		registerWriter(RecipeType.CRAFTING_SHAPELESS, new RecipeWriter<ShapelessRecipe>() {
			@Override
			protected void writeRecipeData(ByteBuf to, ProtocolVersion version, ShapelessRecipe recipe) {
				StringSerializer.writeVarIntUTF8String(to, recipe.getGroup());
				ArraySerializer.writeVarIntTArray(to, recipe.getIngredients(), (lTo, ingredient) -> writeIngredient(lTo, version, ingredient));
				ItemStackSerializer.writeItemStack(to, version, I18NData.DEFAULT_LOCALE, recipe.getResult());
			}
		});
		registerWriter(RecipeType.CRAFTING_SHAPED, new RecipeWriter<ShapedRecipe>() {
			@Override
			protected void writeRecipeData(ByteBuf to, ProtocolVersion version, ShapedRecipe recipe) {
				VarNumberSerializer.writeVarInt(to, recipe.getWidth());
				VarNumberSerializer.writeVarInt(to, recipe.getHeight());
				StringSerializer.writeVarIntUTF8String(to, recipe.getGroup());
				for (RecipeIngredient ingredient : recipe.getIngredients()) {
					writeIngredient(to, version, ingredient);
				}
				ItemStackSerializer.writeItemStack(to, version, I18NData.DEFAULT_LOCALE, recipe.getResult());
			}
		});
		RecipeWriter<SmeltingRecipe> smeltingRecipeWriter = new RecipeWriter<SmeltingRecipe>() {
			@Override
			protected void writeRecipeData(ByteBuf to, ProtocolVersion version, SmeltingRecipe recipe) {
				StringSerializer.writeVarIntUTF8String(to, recipe.getGroup());
				writeIngredient(to, version, recipe.getIngredient());
				ItemStackSerializer.writeItemStack(to, version, I18NData.DEFAULT_LOCALE, recipe.getResult());
				to.writeFloat(recipe.getExp());
				VarNumberSerializer.writeVarInt(to, recipe.getTime());
			}
		};
		registerWriter(RecipeType.SMELTING, smeltingRecipeWriter);
		registerWriter(RecipeType.BLASTING, smeltingRecipeWriter);
		registerWriter(RecipeType.SMOKING, smeltingRecipeWriter);
		registerWriter(RecipeType.CAMPFIRE_COOKING, smeltingRecipeWriter);
		registerWriter(RecipeType.STONECUTTING, new RecipeWriter<StonecuttingRecipe>() {
			@Override
			protected void writeRecipeData(ByteBuf to, ProtocolVersion version, StonecuttingRecipe recipe) {
				StringSerializer.writeVarIntUTF8String(to, recipe.getGroup());
				writeIngredient(to, version, recipe.getIngredient());
				ItemStackSerializer.writeItemStack(to, version, I18NData.DEFAULT_LOCALE, recipe.getResult());
			}
		});

	}

	protected static void writeIngredient(ByteBuf to, ProtocolVersion version, RecipeIngredient ingredient) {
		NetworkItemStack[] possibleStacks = ingredient.getPossibleItemStacks();
		VarNumberSerializer.writeVarInt(to, possibleStacks.length);
		for (int i = 0; i < possibleStacks.length; i++) {
			ItemStackSerializer.writeItemStack(to, version, I18NData.DEFAULT_LOCALE, possibleStacks[i]);
		}
	}

	protected static class RecipeWriter<T extends Recipe> {

		public static final RecipeWriter<Recipe> SIMPLE = new RecipeWriter<>();

		public boolean writeRecipe(ByteBuf to, ProtocolVersion version, T recipe) {
			StringSerializer.writeVarIntUTF8String(to, recipe.getType().getInternalName());
			StringSerializer.writeVarIntUTF8String(to, recipe.getId());
			writeRecipeData(to, version, recipe);
			return true;
		}

		protected void writeRecipeData(ByteBuf to, ProtocolVersion version, T recipe) {
		}

	}

	public DeclareRecipes(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<? extends IPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_DECLARE_RECIPES);
		ArraySerializer.writeVarIntTArray(serializer, to -> {
			int writtenRecipeCount = 0;
			for (Recipe recipe : recipes) {
				@SuppressWarnings("unchecked")
				RecipeWriter<Recipe> writer = (RecipeWriter<Recipe>) recipeWriters.get(recipe.getType());
				if ((writer != null) && writer.writeRecipe(to, version, recipe)) {
					writtenRecipeCount++;
				}
			}
			return writtenRecipeCount;
		});
		return RecyclableSingletonList.create(serializer);
	}

}
