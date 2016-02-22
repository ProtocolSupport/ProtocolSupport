package protocolsupport.injector;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemAnvil;
import net.minecraft.server.v1_8_R3.ItemBlock;
import net.minecraft.server.v1_8_R3.ItemCloth;
import net.minecraft.server.v1_8_R3.ItemSpade;
import net.minecraft.server.v1_8_R3.MinecraftKey;
import net.minecraft.server.v1_8_R3.TileEntity;
import protocolsupport.server.block.BlockAnvil;
import protocolsupport.server.block.BlockCarpet;
import protocolsupport.server.block.BlockEnchantTable;
import protocolsupport.server.block.BlockSnow;
import protocolsupport.server.item.ItemSnow;
import protocolsupport.server.tileentity.TileEntityEnchantTable;
import protocolsupport.utils.Utils;

public class ServerInjector {

	public static void inject() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		registerTileEntity(TileEntityEnchantTable.class, "EnchantTable");
		registerBlock(116, "enchanting_table", new BlockEnchantTable());
		registerBlock(145, "anvil", new ItemAnvil(new BlockAnvil()).b("anvil"));
		registerBlock(171, "carpet", new ItemCloth(new BlockCarpet()).b("woolCarpet"));
		registerBlock(78, "snow_layer", new ItemSnow(new BlockSnow()));
		fixBlocksRefs();
		fixShovel();
		Bukkit.resetRecipes();
	}

	@SuppressWarnings("unchecked")
	private static void registerTileEntity(Class<? extends TileEntity> entityClass, String name) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		((Map<String, Class<? extends TileEntity>>) Utils.setAccessible(TileEntity.class.getDeclaredField("f")).get(null)).put(name, entityClass);
		((Map<Class<? extends TileEntity>, String>) Utils.setAccessible(TileEntity.class.getDeclaredField("g")).get(null)).put(entityClass, name);
	}

	@SuppressWarnings("unchecked")
	private static void registerBlock(int id, String name, Block block) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		MinecraftKey stringkey = new MinecraftKey(name);
		ItemBlock itemblock = new ItemBlock(block);
		Block.REGISTRY.a(id, stringkey, block);
		Iterator<IBlockData> blockdataiterator = block.P().a().iterator();
		while (blockdataiterator.hasNext()) {
			IBlockData blockdata = blockdataiterator.next();
			final int stateId = (id << 4) | block.toLegacyData(blockdata);
			Block.d.a(blockdata, stateId);
		}
		Item.REGISTRY.a(id, stringkey, itemblock);
		((Map<Block, Item>)Utils.setAccessible(Item.class.getDeclaredField("a")).get(null)).put(block, itemblock);
	}

	@SuppressWarnings("unchecked")
	private static void registerBlock(int id, String name, ItemBlock itemblock) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		MinecraftKey stringkey = new MinecraftKey(name);
		Block.REGISTRY.a(id, stringkey, itemblock.d());
		Iterator<IBlockData> blockdataiterator = itemblock.d().P().a().iterator();
		while (blockdataiterator.hasNext()) {
			IBlockData blockdata = blockdataiterator.next();
			final int stateId = (id << 4) | itemblock.d().toLegacyData(blockdata);
			Block.d.a(blockdata, stateId);
		}
		Item.REGISTRY.a(id, stringkey, itemblock);
		((Map<Block, Item>) Utils.setAccessible(Item.class.getDeclaredField("a")).get(null)).put(itemblock.d(), itemblock);
	}

	private static void fixBlocksRefs() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		for (Field field : Blocks.class.getDeclaredFields()) {
			field.setAccessible(true);
			if (Block.class.isAssignableFrom(field.getType())) {
				Block block = (Block) field.get(null);
				Block newblock = Block.getById(Block.getId(block));
				if (block != newblock) {
					Utils.setStaticFinalField(field, newblock);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void fixShovel() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Set<Block> blocks = (Set<Block>) Utils.setAccessible(ItemSpade.class.getDeclaredField("c")).get(null);
		blocks.add(Blocks.SNOW_LAYER);
	}

}
