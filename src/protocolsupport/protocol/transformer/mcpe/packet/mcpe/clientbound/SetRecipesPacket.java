package protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import net.minecraft.server.v1_8_R3.CraftingManager;
import net.minecraft.server.v1_8_R3.IRecipe;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.ShapedRecipes;
import net.minecraft.server.v1_8_R3.ShapelessRecipes;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.id.RemappingTable;
import protocolsupport.utils.Allocator;
import protocolsupport.utils.ChannelUtils;

public class SetRecipesPacket implements ClientboundPEPacket {

	private static final int SHAPELESS = 0;
	private static final int SHAPED = 1;
	private static final int FURNACE = 2;
	private static final int FURNACE_DATA = 3;
	private static final int ENCHANT_LIST = 4;

	@Override
	public int getId() {
		return PEPacketIDs.CRAFTING_DATA_PACKET;
	}

	private static final byte[] toBuf(ShapelessRecipes recipe) throws HasItemRemapException {
		PacketDataSerializer serializer = new PacketDataSerializer(Allocator.allocateBuffer(), ProtocolVersion.MINECRAFT_PE);
		try {
			serializer.writeInt(recipe.getIngredients().size());
			for (ItemStack ingr : recipe.getIngredients()) {
				serializer.writeItemStack(normalize(validate(ingr)));
			}
			serializer.writeInt(1);
			serializer.writeItemStack(validate(recipe.result));
			serializer.writeUUID(UUID.randomUUID());
			return ChannelUtils.toArray(serializer);
		} finally {
			serializer.release();
		}
	}

	private static final byte[] toBuf(ShapedRecipes recipe) throws HasItemRemapException {
		PacketDataSerializer serializer = new PacketDataSerializer(Allocator.allocateBuffer(), ProtocolVersion.MINECRAFT_PE);
		try {
			String[] shape = recipe.toBukkitRecipe().getShape();
			int width = shape[0].length();
			int height = shape.length;
			serializer.writeInt(width);
			serializer.writeInt(height);
			List<ItemStack> ingredients = recipe.getIngredients();
			for (int h = 0; h < height; h++) {
				for (int w = 0; w < width; w++) {
					serializer.writeItemStack(normalize(validate(ingredients.get(w + h * width))));
				}
			}
			serializer.writeInt(1);
			serializer.writeItemStack(validate(recipe.result));
			serializer.writeUUID(UUID.randomUUID());
			return ChannelUtils.toArray(serializer);
		} finally {
			serializer.release();
		}
	}

	private static final RemappingTable itemRemapper = IdRemapper.ITEM.getTable(ProtocolVersion.MINECRAFT_PE);
	private static ItemStack validate(ItemStack itemstack) throws HasItemRemapException {
		if (itemstack == null) {
			return null;
		}
		int itemId = Item.getId(itemstack.getItem());
		if (itemRemapper.getRemap(itemId) != itemId) {
			throw new HasItemRemapException();
		}
		return itemstack;
	}

	private static final ItemStack normalize(ItemStack itemstack) {
		if (itemstack == null) {
			return null;
		}
		itemstack = itemstack.cloneItemStack();
		if (itemstack.count <= 0) {
			itemstack.count = 1;
		}
		if (itemstack.getData() == Short.MAX_VALUE) {
			itemstack.setData(-1);
		}
		return itemstack;
	}

	private static volatile byte[] cachedRecipes = null;
	private static byte[] getRecipesData() {
		if (cachedRecipes == null) {
			ArrayList<ByteBuf> recipesBufs = new ArrayList<ByteBuf>();
			for (IRecipe recipe : CraftingManager.getInstance().getRecipes()) {
				try {
					if (recipe instanceof ShapelessRecipes) {
						ByteBuf recipeBuf = Unpooled.buffer();
						recipeBuf.writeInt(SHAPELESS);
						byte[] data = toBuf((ShapelessRecipes) recipe);
						recipeBuf.writeInt(data.length);
						recipeBuf.writeBytes(data);
						recipesBufs.add(recipeBuf);
					}
					if (recipe instanceof ShapedRecipes) {
						ByteBuf recipeBuf = Unpooled.buffer();
						recipeBuf.writeInt(SHAPED);
						byte[] data = toBuf((ShapedRecipes) recipe);
						recipeBuf.writeInt(data.length);
						recipeBuf.writeBytes(data);
						recipesBufs.add(recipeBuf);
					}
				} catch (HasItemRemapException e) {
				}
			}
			ByteBuf recipesData = Unpooled.buffer();
			recipesData.writeInt(recipesBufs.size());
			for (ByteBuf recipeBuf : recipesBufs) {
				recipesData.writeBytes(recipeBuf);
			}
			recipesData.writeBoolean(true);
			cachedRecipes = ChannelUtils.toArray(recipesData);
		}
		return cachedRecipes;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		buf.writeBytes(getRecipesData());
		return this;
	}

	private static final class HasItemRemapException extends Exception {
		private static final long serialVersionUID = -3976983293616786493L;
		@Override
		public HasItemRemapException fillInStackTrace() {
			return this;
		}
	}

}
