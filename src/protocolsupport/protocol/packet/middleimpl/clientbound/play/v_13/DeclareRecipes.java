package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13;

import java.util.EnumMap;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleDeclareRecipes;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.utils.NamespacedKeyUtils;
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
		registerWriter(RecipeType.CRAFTING_SPECIAL_REPAIRITEM, RecipeWriter.SIMPLE);
		registerWriter(RecipeType.CRAFTING_SPECIAL_TIPPEDARROW, RecipeWriter.SIMPLE);
		registerWriter(RecipeType.CRAFTING_SPECIAL_BANNERDUPLICATE, RecipeWriter.SIMPLE);
		registerWriter(RecipeType.CRAFTING_SPECIAL_BANNERADDPATTERN, RecipeWriter.SIMPLE);
		registerWriter(RecipeType.CRAFTING_SPECIAL_SHIELDDECORATION, RecipeWriter.SIMPLE);
		registerWriter(RecipeType.CRAFTING_SPECIAL_SHULKERBOXCOLORING, RecipeWriter.SIMPLE);
		registerWriter(RecipeType.CRAFTING_SHAPELESS, new RecipeWriter<ShapelessRecipe>() {
			@Override
			protected void writeRecipeData(ByteBuf to, ProtocolVersion version, ShapelessRecipe recipe) {
				StringSerializer.writeString(to, version, recipe.getGroup());
				ArraySerializer.writeVarIntTArray(to, recipe.getIngredients(), (lTo, ingredient) -> writeIngredient(lTo, version, ingredient));
				ItemStackSerializer.writeItemStack(to, version, I18NData.DEFAULT_LOCALE, recipe.getResult());
			}
		});
		registerWriter(RecipeType.CRAFTING_SHAPED, new RecipeWriter<ShapedRecipe>() {
			@Override
			protected void writeRecipeData(ByteBuf to, ProtocolVersion version, ShapedRecipe recipe) {
				VarNumberSerializer.writeVarInt(to, recipe.getWidth());
				VarNumberSerializer.writeVarInt(to, recipe.getHeight());
				StringSerializer.writeString(to, version, recipe.getGroup());
				for (Ingredient ingredient : recipe.getIngredients()) {
					writeIngredient(to, version, ingredient);
				}
				ItemStackSerializer.writeItemStack(to, version, I18NData.DEFAULT_LOCALE, recipe.getResult());
			}
		});
		registerWriter(RecipeType.SMELTING, new RecipeWriter<SmeltingRecipe>() {
			@Override
			protected void writeRecipeData(ByteBuf to, ProtocolVersion version, SmeltingRecipe recipe) {
				StringSerializer.writeString(to, version, recipe.getGroup());
				writeIngredient(to, version, recipe.getIngredient());
				ItemStackSerializer.writeItemStack(to, version, I18NData.DEFAULT_LOCALE, recipe.getResult());
				to.writeFloat(recipe.getExp());
				VarNumberSerializer.writeVarInt(to, recipe.getTime());
			}
		});
	}

	protected static void writeIngredient(ByteBuf to, ProtocolVersion version, Ingredient ingredient) {
		NetworkItemStack[] possibleStacks = ingredient.getPossibleStacks();
		VarNumberSerializer.writeVarInt(to, possibleStacks.length);
		for (int i = 0; i < possibleStacks.length; i++) {
			ItemStackSerializer.writeItemStack(to, version, I18NData.DEFAULT_LOCALE, possibleStacks[i]);
		}
	}

	protected static class RecipeWriter<T extends Recipe> {

		public static final RecipeWriter<Recipe> SIMPLE = new RecipeWriter<>();

		public void writeRecipe(ByteBuf to, ProtocolVersion version, T recipe) {
			StringSerializer.writeString(to, version, recipe.getId());
			StringSerializer.writeString(to, version, NamespacedKeyUtils.fromString(recipe.getType().getInternalName()).getKey());
			writeRecipeData(to, version, recipe);
		}

		protected void writeRecipeData(ByteBuf to, ProtocolVersion version, T recipe) {
		}

	}

	public DeclareRecipes(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_DECLARE_RECIPES);
		ArraySerializer.writeVarIntTArray(serializer, to -> {
			int writtenRecipeCount = 0;
			for (Recipe recipe : recipes) {
				@SuppressWarnings("unchecked")
				RecipeWriter<Recipe> writer = (RecipeWriter<Recipe>) recipeWriters.get(recipe.getType());
				if (writer != null) {
					writtenRecipeCount++;
					writer.writeRecipe(to, version, recipe);
				}
			}
			return writtenRecipeCount;
		});
		return RecyclableSingletonList.create(serializer);
	}

}
