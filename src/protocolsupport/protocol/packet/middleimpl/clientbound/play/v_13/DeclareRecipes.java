package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleDeclareRecipes;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupport.protocol.utils.types.NetworkItemStack;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class DeclareRecipes extends MiddleDeclareRecipes {

	public DeclareRecipes(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ByteBuf data = cachedBuffers.get(connection.getVersion());
		if (data == null) {
			data = Unpooled.buffer();
			ProtocolVersion version = connection.getVersion();
			VarNumberSerializer.writeVarInt(data, recipes.length);
			for (Recipe r : recipes) {
				writeRecipe(r, data, version);
			}
			cachedBuffers.put(version, data);
		}
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_DECLARE_RECIPES);
		serializer.writeBytes(data.slice());
		return RecyclableSingletonList.create(serializer);
	}

	private void writeRecipe(Recipe recipe, ByteBuf serializer, ProtocolVersion version) {
		StringSerializer.writeString(serializer, version, recipe.getId());
		StringSerializer.writeString(serializer, version, recipe.getType().getInternalName());

		if (recipe instanceof ShapelessRecipe) {
			ShapelessRecipe shapelessRecipe = (ShapelessRecipe) recipe;
			StringSerializer.writeString(serializer, version, shapelessRecipe.getGroup());
			Ingredient[] ingredients = shapelessRecipe.getIngredients();
			VarNumberSerializer.writeVarInt(serializer, ingredients.length);
			for (int i = 0; i < ingredients.length; i++) {
				writeIngredient(ingredients[i], serializer, version);
			}
			ItemStackSerializer.writeItemStack(serializer, version, I18NData.DEFAULT_LOCALE, shapelessRecipe.getResult(), true);
		}

		if (recipe instanceof ShapedRecipe) {
			ShapedRecipe shapedRecipe = (ShapedRecipe) recipe;
			VarNumberSerializer.writeVarInt(serializer, shapedRecipe.getWidth());
			VarNumberSerializer.writeVarInt(serializer, shapedRecipe.getHeight());
			StringSerializer.writeString(serializer, version, shapedRecipe.getGroup());
			Ingredient[] ingredients = shapedRecipe.getIngredients();
			for (int i = 0; i < ingredients.length; i++) {
				writeIngredient(ingredients[i], serializer, version);
			}
			ItemStackSerializer.writeItemStack(serializer, version, I18NData.DEFAULT_LOCALE, shapedRecipe.getResult(), true);
		}

		if (recipe instanceof SmeltingRecipe) {
			SmeltingRecipe smeltingRecipe = (SmeltingRecipe) recipe;
			StringSerializer.writeString(serializer, version, smeltingRecipe.getGroup());
			writeIngredient(smeltingRecipe.getIngredient(), serializer, version);
			ItemStackSerializer.writeItemStack(serializer, version, I18NData.DEFAULT_LOCALE, smeltingRecipe.getResult(), true);
			serializer.writeFloat(smeltingRecipe.getExp());
			VarNumberSerializer.writeVarInt(serializer, smeltingRecipe.getTime());
		}
	}

	private void writeIngredient(Ingredient ingredient, ByteBuf serializer, ProtocolVersion version) {
		NetworkItemStack[] possibleStacks = ingredient.getPossibleStacks();
		VarNumberSerializer.writeVarInt(serializer, possibleStacks.length);
		for (int i = 0; i < possibleStacks.length; i++) {
			ItemStackSerializer.writeItemStack(serializer, version, I18NData.DEFAULT_LOCALE, possibleStacks[i], true);
		}
	}
}
