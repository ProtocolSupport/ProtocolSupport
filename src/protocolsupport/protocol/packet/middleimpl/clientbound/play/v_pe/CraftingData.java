package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleDeclareRecipes;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEItems;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.typeremapper.pe.inventory.PETransactionRemapper;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupport.protocol.utils.types.NetworkItemStack;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CraftingData extends MiddleDeclareRecipes {
	protected int recipesWritten;

	public CraftingData(ConnectionImpl connection) {
		super(connection);
	}

	void expandRecipe(Ingredient[] ingredients, Consumer<List<NetworkItemStack>> consumer) {
		//TODO: brute force way of comparing ingredient sets
		Set<Set<PETransactionRemapper.ItemKey>> ingredientSets = new HashSet<>();
		for (Ingredient ingredient : ingredients) {
			if (ingredient.getPossibleStacks().length <= 1) { // these come in empty sometime
				continue;
			}
			Stream<PETransactionRemapper.ItemKey> stream = Arrays.stream(ingredient.getPossibleStacks())
				.map(t -> new PETransactionRemapper.ItemKey(t));
			ingredientSets.add(stream.collect(Collectors.toSet()));
		}
		if (ingredientSets.size() > 1) { //multiple sets, make all permutations
			expandRecipePermutate(new LinkedList<>(Arrays.asList(ingredients)), new LinkedList<>(), consumer);
		} else if (ingredientSets.size() == 1) { //only one set, so do only single-type permutations
			for (PETransactionRemapper.ItemKey ingredientKey : ingredientSets.iterator().next()) {
				NetworkItemStack ingredient = ingredientKey.getKeyItem();
				List<NetworkItemStack> outIngredients = new ArrayList<>();
				for (Ingredient ing : ingredients) {
					if (ing.getPossibleStacks().length == 0) {
						outIngredients.add(null); //air
					} else if (ing.getPossibleStacks().length > 1) {
						outIngredients.add(ingredient); //from set
					} else {
						outIngredients.add(ing.getPossibleStacks()[0]);
					}
				}
				consumer.accept(outIngredients);
			}
		} else { //no sets, straight up recipe
			List<NetworkItemStack> outIngredients = new ArrayList<>();
			for (Ingredient ing : ingredients) {
				if (ing.getPossibleStacks().length == 0) {
					outIngredients.add(null); //air
				} else {
					outIngredients.add(ing.getPossibleStacks()[0]);
				}
			}
			consumer.accept(outIngredients);
		}
	}

	void expandRecipePermutate(LinkedList<Ingredient> ingredients, List<NetworkItemStack> items, Consumer<List<NetworkItemStack>> consumer) {
		LinkedList<Ingredient> tail = new LinkedList<>(ingredients);
		Ingredient first = tail.removeFirst();
		NetworkItemStack[] possibleStacks = first.getPossibleStacks();
		if (possibleStacks.length == 0) {
			possibleStacks = new NetworkItemStack[]{null}; //air
		}
		for (NetworkItemStack item : possibleStacks) {
			LinkedList<NetworkItemStack> newItems = new LinkedList<>(items);
			newItems.add(item);
			if (tail.isEmpty()) {
				//final step
				consumer.accept(newItems);
			} else {
				//recursively call with each new level of permutation
				expandRecipePermutate(tail, newItems, consumer);
			}
		}
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		recipesWritten = 0;
		ByteBuf buffer = Unpooled.buffer();
		for (Recipe recipe : recipes) {
			if(recipe instanceof ShapelessRecipe) {
				ShapelessRecipe shapeless = (ShapelessRecipe)recipe;

				expandRecipe(shapeless.getIngredients(), ingredients -> {
					addRecipeShapeless(buffer, shapeless.getResult(), ingredients);
				});
			} else if(recipe instanceof ShapedRecipe) {
				ShapedRecipe shaped = (ShapedRecipe)recipe;

				expandRecipe(shaped.getIngredients(), ingredients -> {
					addRecipeShaped(buffer, shaped.getResult(), shaped.getWidth(), shaped.getHeight(), ingredients);
				});
			} else if(recipe instanceof SmeltingRecipe) {
				SmeltingRecipe smelting = (SmeltingRecipe)recipe;
				expandRecipe(new Ingredient[]{smelting.getIngredient()}, ingredients -> {
					addRecipeFurnace(buffer, smelting.getResult(), ingredients.get(0));
				});
			}
		}
		ClientBoundPacketData craftPacket = ClientBoundPacketData.create(PEPacketIDs.CRAFTING_DATA);
		VarNumberSerializer.writeVarInt(craftPacket, recipesWritten);
		craftPacket.writeBytes(buffer);
		return RecyclableSingletonList.create(craftPacket);
	}

	protected void addRecipeShaped(ByteBuf to, NetworkItemStack output, int width, int height, List<NetworkItemStack> required) {
		VarNumberSerializer.writeSVarInt(to, 1); //recipe type
		VarNumberSerializer.writeSVarInt(to, width);
		VarNumberSerializer.writeSVarInt(to, height);
		for (NetworkItemStack stack : required) {
			ItemStackSerializer.writeItemStack(to, connection.getVersion(), I18NData.DEFAULT_LOCALE, stack, true);
		}
		VarNumberSerializer.writeVarInt(to, 1); // result item count (PC only supports one itemstack output, so hardcoded to 1)
		ItemStackSerializer.writeItemStack(to, connection.getVersion(), I18NData.DEFAULT_LOCALE, output, true);
		MiscSerializer.writeUUID(to, connection.getVersion(), UUID.nameUUIDFromBytes(to.array()));
		recipesWritten++;
	}

	protected void addRecipeShapeless(ByteBuf to, NetworkItemStack output, List<NetworkItemStack> required) {
		VarNumberSerializer.writeSVarInt(to, 0); //recipe type
		VarNumberSerializer.writeVarInt(to, required.size());
		for (NetworkItemStack stack : required) {
			ItemStackSerializer.writeItemStack(to, connection.getVersion(), I18NData.DEFAULT_LOCALE, stack, true);
		}
		VarNumberSerializer.writeVarInt(to, 1); // result item count (PC only supports one itemstack output, so hardcoded to 1)
		ItemStackSerializer.writeItemStack(to, connection.getVersion(), I18NData.DEFAULT_LOCALE, output, true);
		MiscSerializer.writeUUID(to, connection.getVersion(), UUID.nameUUIDFromBytes(to.array()));
		recipesWritten++;
	}

	protected void addRecipeFurnace(ByteBuf to, NetworkItemStack output, NetworkItemStack input) {
		int peCombinedId = PEItems.getPECombinedIdByModernId(input.getTypeId());
		if (PEItems.getDataFromPECombinedId(peCombinedId) == 0) {
			VarNumberSerializer.writeSVarInt(to, 2); //recipe type
			VarNumberSerializer.writeSVarInt(to, PEItems.getIdFromPECombinedId(peCombinedId));
			ItemStackSerializer.writeItemStack(to, connection.getVersion(), I18NData.DEFAULT_LOCALE, output, true);
		} else { //meta recipe
			VarNumberSerializer.writeSVarInt(to, 3); //recipe type, with data
			VarNumberSerializer.writeSVarInt(to, PEItems.getIdFromPECombinedId(peCombinedId));
			VarNumberSerializer.writeSVarInt(to, PEItems.getDataFromPECombinedId(peCombinedId));
			ItemStackSerializer.writeItemStack(to, connection.getVersion(), I18NData.DEFAULT_LOCALE, output, true);
		}
		recipesWritten++;
	}
}
