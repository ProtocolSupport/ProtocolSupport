package protocolsupport.injector;

import net.minecraft.server.v1_8_R1.Block;
import net.minecraft.server.v1_8_R1.Blocks;
import net.minecraft.server.v1_8_R1.IBlockData;
import net.minecraft.server.v1_8_R1.Item;
import net.minecraft.server.v1_8_R1.ItemBlock;
import net.minecraft.server.v1_8_R1.MinecraftKey;
import net.minecraft.server.v1_8_R1.TileEntity;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;

import protocolsupport.server.block.BlockEnchantTable;
import protocolsupport.server.tileentity.TileEntityEnchantTable;
import protocolsupport.utils.Utils;

public class ServerInjector {

	public static void inject() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		registerTileEntity(TileEntityEnchantTable.class, "EnchantTable");
		registerBlock(116, "enchanting_table", new BlockEnchantTable());
		fixBlocksRefs();
	}

	@SuppressWarnings("unchecked")
	private static void registerTileEntity(Class<? extends TileEntity> entityClass, String name) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		((Map<String, Class<? extends TileEntity>>) Utils.<Field>setAccessible(TileEntity.class.getDeclaredField("f")).get(null)).put(name, entityClass);
		((Map<Class<? extends TileEntity>, String>) Utils.<Field>setAccessible(TileEntity.class.getDeclaredField("g")).get(null)).put(entityClass, name);
	}

	@SuppressWarnings("unchecked")
	private static void registerBlock(int id, String name, Block block) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		MinecraftKey stringkey = new MinecraftKey(name);
		ItemBlock itemblock = new ItemBlock(block);
		Block.REGISTRY.a(id, stringkey, block);
		Iterator<IBlockData> blockdataiterator = block.O().a().iterator();
        while (blockdataiterator.hasNext()) {
        	IBlockData blockdata = blockdataiterator.next();
            final int stateId = id << 4 | block.toLegacyData(blockdata);
            Block.d.a(blockdata, stateId);
        }
		Item.REGISTRY.a(id, stringkey, itemblock);
		((Map<Block, Item>)Utils.<Field>setAccessible(Item.class.getDeclaredField("a")).get(null)).put(block, itemblock);
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

}
