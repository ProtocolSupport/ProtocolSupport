package protocolsupport.injector;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.Bukkit;

import net.minecraft.server.v1_11_R1.Block;
import net.minecraft.server.v1_11_R1.Blocks;
import net.minecraft.server.v1_11_R1.IBlockData;
import net.minecraft.server.v1_11_R1.Item;
import net.minecraft.server.v1_11_R1.ItemBlock;
import net.minecraft.server.v1_11_R1.ItemWaterLily;
import net.minecraft.server.v1_11_R1.MinecraftKey;
import net.minecraft.server.v1_11_R1.RegistryMaterials;
import net.minecraft.server.v1_11_R1.TileEntity;
import protocolsupport.server.block.BlockEnchantTable;
import protocolsupport.server.block.BlockWaterLily;
import protocolsupport.server.tileentity.TileEntityEnchantTable;
import protocolsupport.utils.ReflectionUtils;

public class ServerInjector {

	public static void inject() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		registerTileEntity(TileEntityEnchantTable.class, "enchanting_table");
		registerBlock(116, "enchanting_table", new BlockEnchantTable());
		registerBlock(111, "waterlily", new ItemWaterLily(new BlockWaterLily()));
		fixBlocksRefs();
		Bukkit.resetRecipes();
	}

	@SuppressWarnings("unchecked")
	private static void registerTileEntity(Class<? extends TileEntity> entityClass, String name) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		RegistryMaterials<MinecraftKey, Class<? extends TileEntity>> registry = (RegistryMaterials<MinecraftKey, Class<? extends TileEntity>>) ReflectionUtils.getField(TileEntity.class, "f").get(null);
		registry.a(new MinecraftKey(name), entityClass);
	}

	private static void registerBlock(int id, String name, Block block) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		registerBlock(id, name, new ItemBlock(block));
	}

	@SuppressWarnings("unchecked")
	private static void registerBlock(int id, String name, ItemBlock itemblock) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		MinecraftKey stringkey = new MinecraftKey(name);
		Block block = itemblock.getBlock();
		Block.REGISTRY.a(id, stringkey, block);
		Iterator<IBlockData> blockdataiterator = block.s().a().iterator();
		while (blockdataiterator.hasNext()) {
			IBlockData blockdata = blockdataiterator.next();
			final int stateId = (id << 4) | block.toLegacyData(blockdata);
			Block.REGISTRY_ID.a(blockdata, stateId);
		}
		Item.REGISTRY.a(id, stringkey, itemblock);
		((Map<Block, Item>) ReflectionUtils.setAccessible(Item.class.getDeclaredField("a")).get(null)).put(block, itemblock);
	}

	private static void fixBlocksRefs() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		for (Field field : Blocks.class.getDeclaredFields()) {
			field.setAccessible(true);
			if (Block.class.isAssignableFrom(field.getType())) {
				Block block = (Block) field.get(null);
				Block newblock = Block.getById(Block.getId(block));
				if (block != newblock) {
					Iterator<IBlockData> blockdataiterator = block.s().a().iterator();
					while (blockdataiterator.hasNext()) {
						IBlockData blockdata = blockdataiterator.next();
						ReflectionUtils.getField(blockdata.getClass(), "a").set(blockdata, block);
					}
					ReflectionUtils.setStaticFinalField(field, newblock);
				}
			}
		}
	}

}
