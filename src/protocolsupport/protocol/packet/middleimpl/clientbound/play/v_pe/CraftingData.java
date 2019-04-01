package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleDeclareRecipes;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEItems;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.types.NetworkItemStack;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

import java.util.LinkedList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

//TODO: add repair recipes for all types that can take damade
public class CraftingData extends MiddleDeclareRecipes {

	public static final int PE_RECIPE_TYPE_SHAPELESS = 0;
	public static final int PE_RECIPE_TYPE_SHAPED = 1;
	public static final int PE_RECIPE_TYPE_FURNACE = 2;
	public static final int PE_RECIPE_TYPE_FURNACE_META = 3;

	public static final String TAG_CRAFTING_TABLE = "crafting_table";
	public static final String TAG_FURNACE = "furnace";

	protected int recipesWritten;

	public CraftingData(ConnectionImpl connection) {
		super(connection);
	}

	void expandRecipe(Ingredient[] ingredients, Consumer<List<NetworkItemStack>> consumer) {
		expandRecipe(new LinkedList<>(Arrays.asList(ingredients)), new LinkedList<>(), consumer);
	}

	void expandRecipe(LinkedList<Ingredient> ingredients, List<NetworkItemStack> items, Consumer<List<NetworkItemStack>> consumer) {
		String locale = connection.getCache().getAttributesCache().getLocale();
		LinkedList<Ingredient> tail = new LinkedList<>(ingredients);
		Ingredient first = tail.removeFirst();
		NetworkItemStack[] possibleStacks = first.getPossibleStacks().clone();
		for (int i = 0 ; i < possibleStacks.length ; i++) {
			possibleStacks[i] = ItemStackSerializer.remapItemToClient(connection.getVersion(), locale, possibleStacks[i].cloneItemStack());
		}
		if (possibleStacks.length == 0) { //empty
			possibleStacks = new NetworkItemStack[]{NetworkItemStack.NULL};
		}
		if (possibleStacks.length > 2) { //'large' ingredient set
			NetworkItemStack firstType = possibleStacks[0];
			boolean allSameType = true;
			//see if all ingredients are variants of the same type
			for (NetworkItemStack ing : possibleStacks) {
				if (ing.getTypeId() != firstType.getTypeId() || ing.getAmount() != 1) {
					allSameType = false;
					break;
				}
			}
			if (allSameType) {
				NetworkItemStack wildStack = new NetworkItemStack();
				wildStack.setTypeId(firstType.getTypeId());
				wildStack.setLegacyData(-1); //wildcard stack
				wildStack.setAmount(1);
				possibleStacks = new NetworkItemStack[]{ wildStack };
			}
		}
		for (NetworkItemStack item : possibleStacks) {
			LinkedList<NetworkItemStack> newItems = new LinkedList<>(items);
			newItems.add(item);
			if (tail.isEmpty()) {
				consumer.accept(newItems); //final step
			} else {
				//recursively call with each new level of permutation
				expandRecipe(tail, newItems, consumer);
			}
		}
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		recipesWritten = 0;
		ByteBuf buffer = Unpooled.buffer();
		try {
			for (Recipe recipe : recipes) {
				if (recipe instanceof ShapelessRecipe) {
					ShapelessRecipe shapeless = (ShapelessRecipe) recipe;
					expandRecipe(shapeless.getIngredients(), ingredients -> {
						addRecipeShapeless(buffer, shapeless.getResult(), ingredients);
					});
				} else if (recipe instanceof ShapedRecipe) {
					ShapedRecipe shaped = (ShapedRecipe) recipe;
					expandRecipe(shaped.getIngredients(), ingredients -> {
						addRecipeShaped(buffer, shaped.getResult(), shaped.getWidth(), shaped.getHeight(), ingredients);
					});
				} else if (recipe instanceof SmeltingRecipe) {
					SmeltingRecipe smelting = (SmeltingRecipe) recipe;
					expandRecipe(new Ingredient[]{smelting.getIngredient()}, ingredients -> {
						addRecipeFurnace(buffer, smelting.getResult(), ingredients.get(0));
					});
				}
			}
			ClientBoundPacketData craftPacket = ClientBoundPacketData.create(PEPacketIDs.CRAFTING_DATA);
			VarNumberSerializer.writeVarInt(craftPacket, recipesWritten);
			craftPacket.writeBytes(buffer);
			return RecyclableSingletonList.create(craftPacket);
		} finally {
			buffer.release();
		}
	}

	protected void addRecipeShaped(ByteBuf to, NetworkItemStack output, int width, int height, List<NetworkItemStack> required) {
		String locale = connection.getCache().getAttributesCache().getLocale();
		VarNumberSerializer.writeSVarInt(to, PE_RECIPE_TYPE_SHAPED); //recipe type
		VarNumberSerializer.writeSVarInt(to, width);
		VarNumberSerializer.writeSVarInt(to, height);
		for (NetworkItemStack stack : required) {
			ItemStackSerializer.writePEItemStack(to, stack);
		}
		VarNumberSerializer.writeVarInt(to, 1); // result item count
		ItemStackSerializer.writeItemStack(to, connection.getVersion(), locale, output);
		MiscSerializer.writePEUUID(to, UUID.nameUUIDFromBytes(to.array()));
		if (connection.getVersion().isAfterOrEq(ProtocolVersion.MINECRAFT_PE_1_11)) {
			StringSerializer.writeString(to, connection.getVersion(), TAG_CRAFTING_TABLE);
		}
		recipesWritten++;
	}

	protected void addRecipeShapeless(ByteBuf to, NetworkItemStack output, List<NetworkItemStack> required) {
		String locale = connection.getCache().getAttributesCache().getLocale();
		VarNumberSerializer.writeSVarInt(to, PE_RECIPE_TYPE_SHAPELESS); //recipe type
		VarNumberSerializer.writeVarInt(to, required.size());
		for (NetworkItemStack stack : required) {
			ItemStackSerializer.writePEItemStack(to, stack);
		}
		VarNumberSerializer.writeVarInt(to, 1); // result item count
		ItemStackSerializer.writeItemStack(to, connection.getVersion(), locale, output);
		MiscSerializer.writePEUUID(to, UUID.nameUUIDFromBytes(to.array()));
		if (connection.getVersion().isAfterOrEq(ProtocolVersion.MINECRAFT_PE_1_11)) {
			StringSerializer.writeString(to, connection.getVersion(), TAG_CRAFTING_TABLE);
		}
		recipesWritten++;
	}

	protected void addRecipeFurnace(ByteBuf to, NetworkItemStack output, NetworkItemStack input) {
		String locale = connection.getCache().getAttributesCache().getLocale();
		if (input.getLegacyData() == 0) {
			VarNumberSerializer.writeSVarInt(to, PE_RECIPE_TYPE_FURNACE); //recipe type
			VarNumberSerializer.writeSVarInt(to, input.getTypeId());
		} else { //meta recipe
			VarNumberSerializer.writeSVarInt(to, PE_RECIPE_TYPE_FURNACE_META); //recipe type, with data
			VarNumberSerializer.writeSVarInt(to, input.getTypeId());
			VarNumberSerializer.writeSVarInt(to, input.getLegacyData());
		}
		ItemStackSerializer.writeItemStack(to, connection.getVersion(), locale, output);
		if (connection.getVersion().isAfterOrEq(ProtocolVersion.MINECRAFT_PE_1_11)) {
			StringSerializer.writeString(to, connection.getVersion(), TAG_FURNACE);
		}
		recipesWritten++;
	}
}
