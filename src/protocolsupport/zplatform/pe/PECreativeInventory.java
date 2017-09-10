package protocolsupport.zplatform.pe;

import org.bukkit.Material;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.InventorySetItems;
import protocolsupport.protocol.typeremapper.pe.PEInventory;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PECreativeInventory {
	private static ClientBoundPacketData creativeInventoryPacket;

	public static ClientBoundPacketData getCreativeInventoryPacket() {
		return creativeInventoryPacket;
	}

	public static void generateCreativeInventoryItems() {
		List<Integer> skipMaterials = new ArrayList<Integer>(); // We use Integers because there are some duplicated items in the Material enum

		// Unobtainable items
		skipMaterials.add(Material.AIR.getId());
		skipMaterials.add(Material.PISTON_EXTENSION.getId());
		skipMaterials.add(Material.PISTON_MOVING_PIECE.getId());
		skipMaterials.add(Material.BED_BLOCK.getId());
		skipMaterials.add(Material.ACACIA_DOOR.getId());
		skipMaterials.add(Material.BIRCH_DOOR.getId());
		skipMaterials.add(Material.DARK_OAK_DOOR.getId());
		skipMaterials.add(Material.SPRUCE_DOOR.getId());
		skipMaterials.add(Material.JUNGLE_DOOR.getId());
		skipMaterials.add(Material.BEETROOT_BLOCK.getId());
		skipMaterials.add(Material.CAKE_BLOCK.getId());
		skipMaterials.add(Material.IRON_DOOR_BLOCK.getId());
		skipMaterials.add(Material.NETHER_WART_BLOCK.getId());
		skipMaterials.add(Material.STRUCTURE_BLOCK.getId());
		skipMaterials.add(Material.SUGAR_CANE_BLOCK.getId());
		skipMaterials.add(Material.DIODE_BLOCK_OFF.getId());
		skipMaterials.add(Material.DIODE_BLOCK_ON.getId());
		skipMaterials.add(Material.REDSTONE_COMPARATOR_OFF.getId());
		skipMaterials.add(Material.REDSTONE_COMPARATOR_ON.getId());
		skipMaterials.add(Material.REDSTONE_LAMP_OFF.getId());
		skipMaterials.add(Material.REDSTONE_LAMP_ON.getId());
		skipMaterials.add(Material.REDSTONE_TORCH_OFF.getId());
		skipMaterials.add(Material.REDSTONE_TORCH_ON.getId());
		skipMaterials.add(Material.REDSTONE_WIRE.getId());
		skipMaterials.add(Material.GLOWING_REDSTONE_ORE.getId());
		skipMaterials.add(Material.BARRIER.getId());
		skipMaterials.add(Material.BURNING_FURNACE.getId());
		skipMaterials.add(Material.SOIL.getId());
		skipMaterials.add(Material.POTATO.getId());
		skipMaterials.add(Material.CARROT.getId());
		skipMaterials.add(Material.MAP.getId());
		skipMaterials.add(Material.DOUBLE_STEP.getId());
		skipMaterials.add(Material.DOUBLE_STONE_SLAB2.getId());
		skipMaterials.add(Material.PURPUR_DOUBLE_SLAB.getId());
		skipMaterials.add(Material.WOOD_DOUBLE_STEP.getId());

		// Items that crashes the client (for some reason that we don't know yet... maybe we should blame mojang for this)
		skipMaterials.add(Material.DROPPER.getId());
		skipMaterials.add(Material.BEETROOT.getId());
		skipMaterials.add(Material.BEETROOT_SEEDS.getId());
		skipMaterials.add(Material.BEETROOT_SOUP.getId());
		skipMaterials.add(Material.GOLD_RECORD.getId());
		skipMaterials.add(Material.GREEN_RECORD.getId());
		skipMaterials.add(Material.RECORD_3.getId());
		skipMaterials.add(Material.RECORD_4.getId());
		skipMaterials.add(Material.RECORD_5.getId());
		skipMaterials.add(Material.RECORD_6.getId());
		skipMaterials.add(Material.RECORD_7.getId());
		skipMaterials.add(Material.RECORD_8.getId());
		skipMaterials.add(Material.RECORD_9.getId());
		skipMaterials.add(Material.RECORD_10.getId());
		skipMaterials.add(Material.RECORD_11.getId());
		skipMaterials.add(Material.RECORD_12.getId());

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

		List<ItemStackWrapper> itemStacks = new ArrayList<ItemStackWrapper>();

		for (Material material : Material.values()) {
			if (!skipMaterials.contains(material.getId())) {
				ItemStackWrapper wrapper = ServerPlatform.get().getWrapperFactory().createItemStack(material);
				if (wrapper.getType() == Material.AIR) {
					continue;
				}
				itemStacks.add(wrapper);
				// Adding misc stuff
				if (subIds.containsKey(material)) {
					int maxValue = subIds.get(material);
					for (int i = 1; maxValue >= i; i++) {
						ItemStackWrapper subIdItemWrapper = ServerPlatform.get().getWrapperFactory().createItemStack(material);
						subIdItemWrapper.setData(i);
						itemStacks.add(subIdItemWrapper);
					}
				}
				skipMaterials.add(material.getId());
			}
		}

		creativeInventoryPacket = InventorySetItems.create(ProtocolVersion.MINECRAFT_PE, I18NData.DEFAULT_LOCALE, PEInventory.PESource.POCKET_CREATIVE_INVENTORY, itemStacks.toArray(new ItemStackWrapper[0]));
	}
}