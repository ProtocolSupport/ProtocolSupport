package protocolsupport.zplatform.impl.pe;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.Bukkit;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackRemapper;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupport.utils.IntTuple;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

public class PECraftingManager {

	private static final ProtocolVersion defaultPE = ProtocolVersion.MINECRAFT_PE;
	private static final PECraftingManager instance = new PECraftingManager();

	public static PECraftingManager getInstance() {
		return instance;
	}

	private byte[] recipes;
	private AtomicInteger recipescount = new AtomicInteger();

	public byte[] getAllRecipes() {
		return recipes;
	}
	
	public int getRecipeCount() {
		return recipescount.get();
	}

	public void registerRecipes() {
		ByteBuf recipesbuf = Unpooled.buffer();
		AtomicInteger recipescount = new AtomicInteger();

		Bukkit.recipeIterator().forEachRemaining(recipe -> {
			if (recipe instanceof ShapedRecipe) {
				recipescount.incrementAndGet();
				ShapedRecipe shapedRecipe = (ShapedRecipe) recipe;
				Map<Character, ItemStack> map = shapedRecipe.getIngredientMap();
				String[] pattern = shapedRecipe.getShape();
				int width = pattern[0].length(), height = pattern.length;
				ItemStackWrapper[] required = new ItemStackWrapper[width * height];
				for (int z = 0; z < height; z++) {
					for (int x = 0; x < width; x++) {
						required[(z*width) + x] = fromBukkitStack(map.get(pattern[z].charAt(x)));
					}
				}
				addRecipeShaped(recipesbuf, fromBukkitStack(shapedRecipe.getResult()), width, height, required);
			} else if (recipe instanceof ShapelessRecipe) {
				recipescount.incrementAndGet();
				ShapelessRecipe shapelessRecipe = (ShapelessRecipe) recipe;
				ItemStackWrapper[] required = new ItemStackWrapper[shapelessRecipe.getIngredientList().size()];
				for (int i = 0; i < required.length; i++) {
					required[i] = fromBukkitStack(shapelessRecipe.getIngredientList().get(i));
				}
				addRecipeShapeless(recipesbuf, fromBukkitStack(recipe.getResult()), required);
			} else if (recipe instanceof FurnaceRecipe) {
				recipescount.incrementAndGet();
				FurnaceRecipe shapelessRecipe = (FurnaceRecipe) recipe;
				addRecipeFurnace(recipesbuf, fromBukkitStack(shapelessRecipe.getResult()), fromBukkitStack(shapelessRecipe.getInput()));
			}
		});
		
		byte[] rrecipes = MiscSerializer.readAllBytes(recipesbuf);
		recipesbuf.clear();

		VarNumberSerializer.writeVarInt(recipesbuf, recipescount.get());
		recipesbuf.writeBytes(rrecipes);

		recipes = MiscSerializer.readAllBytes(recipesbuf);
	}

	private static ItemStackWrapper fromBukkitStack(ItemStack stack) {
		return ServerPlatform.get().getWrapperFactory().createItemStack(stack);
	}

	public void addRecipeShaped(ByteBuf to, ItemStackWrapper output, int width, int height, ItemStackWrapper[] required) {
		VarNumberSerializer.writeSVarInt(to, 1); //recipe type
		VarNumberSerializer.writeSVarInt(to, width);
		VarNumberSerializer.writeSVarInt(to, height);
		for (ItemStackWrapper stack : required) {
			ItemStackSerializer.writeItemStack(to, defaultPE, I18NData.DEFAULT_LOCALE, stack, true);
		}
		VarNumberSerializer.writeVarInt(to, 1); // result item count (PC only supports one itemstack output, so hardcoded to 1)
		ItemStackSerializer.writeItemStack(to, defaultPE, I18NData.DEFAULT_LOCALE, output, true);
		MiscSerializer.writeUUID(to, defaultPE, UUID.nameUUIDFromBytes(to.array()));
	}

	public void addRecipeShapeless(ByteBuf to, ItemStackWrapper output, ItemStackWrapper[] required) {
		VarNumberSerializer.writeSVarInt(to, 0); //recipe type
		VarNumberSerializer.writeVarInt(to, required.length);
		for (ItemStackWrapper stack : required) {
			ItemStackSerializer.writeItemStack(to, defaultPE, I18NData.DEFAULT_LOCALE, stack, true);
		}
		VarNumberSerializer.writeVarInt(to, 1); // result item count (PC only supports one itemstack output, so hardcoded to 1)
		ItemStackSerializer.writeItemStack(to, defaultPE, I18NData.DEFAULT_LOCALE, output, true);
		MiscSerializer.writeUUID(to, defaultPE, UUID.nameUUIDFromBytes(to.array()));
	}

	public void addRecipeFurnace(ByteBuf to, ItemStackWrapper output, ItemStackWrapper input) {
		IntTuple iddata = ItemStackRemapper.ID_DATA_REMAPPING_REGISTRY.getTable(defaultPE).getRemap(input.getTypeId(), input.getData());
		if (iddata != null) {
			input.setTypeId(iddata.getI1());
			if (iddata.getI2() != -1) {
				input.setData(iddata.getI2());
			}
		}

		if (input.getData() == 0) {
			VarNumberSerializer.writeSVarInt(to, 2); //recipe type
			VarNumberSerializer.writeSVarInt(to, input.getTypeId());
			ItemStackSerializer.writeItemStack(to, defaultPE, I18NData.DEFAULT_LOCALE, output, true);
		} else { //meta recipe
			VarNumberSerializer.writeSVarInt(to, 3); //recipe type
			VarNumberSerializer.writeSVarInt(to, input.getTypeId());
			VarNumberSerializer.writeSVarInt(to, input.getData());
			ItemStackSerializer.writeItemStack(to, defaultPE, I18NData.DEFAULT_LOCALE, output, true);
		}
	}

}
