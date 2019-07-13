package protocolsupport.protocol.typeremapper.pe.inventory;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

import protocolsupport.api.MaterialAPI;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupportbuildprocessor.Preload;

@Preload
public class PEInventory {

	private static List<Material> clickUpdateMaterials = new ArrayList<Material>(9);

	static {
		clickUpdateMaterials.add(Material.OAK_BOAT);
		clickUpdateMaterials.add(Material.ACACIA_BOAT);
		clickUpdateMaterials.add(Material.BIRCH_BOAT);
		clickUpdateMaterials.add(Material.DARK_OAK_BOAT);
		clickUpdateMaterials.add(Material.JUNGLE_BOAT);
		clickUpdateMaterials.add(Material.SPRUCE_BOAT);
	}

	public static boolean shouldDoClickUpdate(NetworkItemStack itemstack) {
		return !itemstack.isNull() && clickUpdateMaterials.contains(MaterialAPI.getItemByNetworkId(itemstack.getTypeId()));
	}

	//Slot thingy numbers.
	public static class PESource {
		public static final int POCKET_FAUX_DROP = -999;
		public static final int POCKET_BEACON = -24;
		public static final int POCKET_TRADE_OUTPUT = -23;
		public static final int POCKET_TRADE_USE_INGREDIENT = -22;
		public static final int POCKET_TRADE_INPUT_2 = -21;
		public static final int POCKET_TRADE_INPUT_1 = -20;
		public static final int POCKET_ENCHANT_OUTPUT = -16;
		public static final int POCKET_ENCHANT_MATERIAL = -15;
		public static final int POCKET_ENCHANT_INPUT = -14;
		public static final int POCKET_ANVIL_OUTPUT = -13;
		public static final int POCKET_ANVIL_RESULT = -12;
		public static final int POCKET_ANVIL_MATERIAL = -11;
		public static final int POCKET_ANVIL_INPUT = -10;
		public static final int POCKET_CRAFTING_GRID_USE_INGREDIENT = -5;
		public static final int POCKET_CRAFTING_RESULT = -4;
		public static final int POCKET_CRAFTING_GRID_REMOVE = -3;
		public static final int POCKET_CRAFTING_GRID_ADD = -2;
		public static final int POCKET_NONE = -1;
		public static final int POCKET_INVENTORY = 0;
		public static final int POCKET_OFFHAND = 119;
		public static final int POCKET_ARMOR_EQUIPMENT = 120;
		public static final int POCKET_CREATIVE_INVENTORY = 121;
		public static final int POCKET_CLICKED_SLOT = 124;
	}

}
