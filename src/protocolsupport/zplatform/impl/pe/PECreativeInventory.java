package protocolsupport.zplatform.impl.pe;

import org.bukkit.Material;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class PECreativeInventory {
	
	private PECreativeInventory() { }
	private static final PECreativeInventory INSTANCE = new PECreativeInventory();
	
	public static PECreativeInventory getInstance() {
		return INSTANCE;
	}

	private byte[] creativeItems;
	private int itemCount;
	
	public byte[] getCreativeItems() {
		return creativeItems;
	}
	
	public int getItemCount() {
		return itemCount;
	}

	@SuppressWarnings("deprecation")
	public void generateCreativeInventoryItems() {
		//We use Integers because there are some duplicated items in the Material enum.
		List<Material> skipMaterials = new ArrayList<Material>();
		//Unobtainable items
		skipMaterials.add(Material.AIR);
		skipMaterials.add(Material.PISTON_EXTENSION);
		skipMaterials.add(Material.PISTON_MOVING_PIECE);
		skipMaterials.add(Material.BED_BLOCK);
		skipMaterials.add(Material.ACACIA_DOOR);
		skipMaterials.add(Material.BIRCH_DOOR);
		skipMaterials.add(Material.DARK_OAK_DOOR);
		skipMaterials.add(Material.SPRUCE_DOOR);
		skipMaterials.add(Material.JUNGLE_DOOR);
		skipMaterials.add(Material.BEETROOT_BLOCK);
		skipMaterials.add(Material.CAKE_BLOCK);
		skipMaterials.add(Material.IRON_DOOR_BLOCK);
		skipMaterials.add(Material.NETHER_WART_BLOCK);
		skipMaterials.add(Material.STRUCTURE_BLOCK);
		skipMaterials.add(Material.SUGAR_CANE_BLOCK);
		skipMaterials.add(Material.DIODE_BLOCK_OFF);
		skipMaterials.add(Material.DIODE_BLOCK_ON);
		skipMaterials.add(Material.REDSTONE_COMPARATOR_OFF);
		skipMaterials.add(Material.REDSTONE_COMPARATOR_ON);
		skipMaterials.add(Material.REDSTONE_LAMP_OFF);
		skipMaterials.add(Material.REDSTONE_LAMP_ON);
		skipMaterials.add(Material.REDSTONE_TORCH_OFF);
		skipMaterials.add(Material.REDSTONE_TORCH_ON);
		skipMaterials.add(Material.REDSTONE_WIRE);
		skipMaterials.add(Material.GLOWING_REDSTONE_ORE);
		skipMaterials.add(Material.BARRIER);
		skipMaterials.add(Material.BURNING_FURNACE);
		skipMaterials.add(Material.SOIL);
		skipMaterials.add(Material.POTATO);
		skipMaterials.add(Material.CARROT);
		skipMaterials.add(Material.MAP);
		skipMaterials.add(Material.DOUBLE_STEP);
		skipMaterials.add(Material.DOUBLE_STONE_SLAB2);
		skipMaterials.add(Material.PURPUR_DOUBLE_SLAB);
		skipMaterials.add(Material.WOOD_DOUBLE_STEP);
		
		//Items that crashes the client (for some reason that we don't know yet... maybe we should blame mojang for this)
		skipMaterials.add(Material.DROPPER);
		skipMaterials.add(Material.BEETROOT);
		skipMaterials.add(Material.BEETROOT_SEEDS);
		skipMaterials.add(Material.BEETROOT_SOUP);
		skipMaterials.add(Material.GOLD_RECORD);
		skipMaterials.add(Material.GREEN_RECORD);
		skipMaterials.add(Material.RECORD_3);
		skipMaterials.add(Material.RECORD_4);
		skipMaterials.add(Material.RECORD_5);
		skipMaterials.add(Material.RECORD_6);
		skipMaterials.add(Material.RECORD_7);
		skipMaterials.add(Material.RECORD_8);
		skipMaterials.add(Material.RECORD_9);
		skipMaterials.add(Material.RECORD_10);
		skipMaterials.add(Material.RECORD_11);
		skipMaterials.add(Material.RECORD_12);
		
		//Data values.
		Map<Material, Integer> subIds = new HashMap<Material, Integer>();
		subIds.put(Material.STONE, 6);
		subIds.put(Material.WOOD, 5);
		subIds.put(Material.SAPLING, 5);
		subIds.put(Material.SAND, 1);
		subIds.put(Material.LOG, 3);
		subIds.put(Material.LEAVES, 3);
		subIds.put(Material.SPONGE, 1);
		subIds.put(Material.SANDSTONE, 2);
		subIds.put(Material.WOOL, 15);
		subIds.put(Material.STAINED_GLASS, 15);
		subIds.put(Material.STAINED_GLASS_PANE, 15);
		subIds.put(Material.STAINED_CLAY, 15);
		subIds.put(Material.CONCRETE, 15);
		subIds.put(Material.CONCRETE_POWDER, 15);
		subIds.put(Material.CARPET, 15);
		subIds.put(Material.RED_ROSE, 8);
		subIds.put(Material.STEP, 7);
		subIds.put(Material.SMOOTH_BRICK, 3);
		subIds.put(Material.WOOD_STEP, 5);
		subIds.put(Material.COBBLE_WALL, 1);
		subIds.put(Material.QUARTZ_BLOCK, 2);
		subIds.put(Material.LEAVES_2, 1);
		subIds.put(Material.LOG_2, 1);
		subIds.put(Material.PRISMARINE, 2);
		subIds.put(Material.DOUBLE_PLANT, 5);
		subIds.put(Material.RED_SANDSTONE, 2);
		
		//Cycle through the bukkit materials.
		ByteBuf itembuf = Unpooled.buffer();
		AtomicInteger itemcount = new AtomicInteger();
		Arrays.stream(Material.values())
			.filter(m -> !skipMaterials.contains(m))
			.map(m -> ServerPlatform.get().getWrapperFactory().createItemStack(m.getId()))
			.filter(w -> w.getTypeId() != 0)
			.forEach(wrapper -> {
				ItemStackSerializer.writeItemStack(itembuf, ProtocolVersion.MINECRAFT_PE, I18NData.DEFAULT_LOCALE, wrapper, true);
				itemcount.incrementAndGet();
				if (subIds.containsKey(wrapper.getType())) {
					IntStream.range(1, subIds.get(wrapper.getType())).forEach(i -> {
						ItemStackWrapper subIdItemWrapper = wrapper.cloneItemStack();
						subIdItemWrapper.setData(i);
						ItemStackSerializer.writeItemStack(itembuf, ProtocolVersion.MINECRAFT_PE, I18NData.DEFAULT_LOCALE, subIdItemWrapper, true);
						itemcount.getAndIncrement();
					});;
				}
			});
		itemCount = itemcount.get();
		creativeItems = MiscSerializer.readAllBytes(itembuf);
	}
}