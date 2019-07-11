package protocolsupport.protocol.typeremapper.tile;

import java.util.List;
import java.util.function.Consumer;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

import protocolsupport.api.MaterialAPI;
import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper.TileEntityWithBlockDataNBTRemapper;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTInt;
import protocolsupport.utils.CollectionsUtils.ArrayMap;
import protocolsupport.utils.CollectionsUtils.ArrayMap.Entry;

class TileEntityBedRemapper extends TileEntityWithBlockDataNBTRemapper {

	protected void register(List<Entry<Consumer<NBTCompound>>> list, Material bed, int color) {
		for (BlockData blockdata : MaterialAPI.getBlockDataList(bed)) {
			list.add(new ArrayMap.Entry<>(MaterialAPI.getBlockDataNetworkId(blockdata), nbt -> {
				nbt.setTag("color", new NBTInt(color));
			}));
		}
	}

	@Override
	protected void init(List<Entry<Consumer<NBTCompound>>> list) {
		register(list, Material.WHITE_BED, 0);
		register(list, Material.ORANGE_BED, 1);
		register(list, Material.MAGENTA_BED, 2);
		register(list, Material.LIGHT_BLUE_BED, 3);
		register(list, Material.YELLOW_BED, 4);
		register(list, Material.LIME_BED, 5);
		register(list, Material.PINK_BED, 6);
		register(list, Material.GRAY_BED, 7);
		register(list, Material.LIGHT_GRAY_BED, 8);
		register(list, Material.CYAN_BED, 9);
		register(list, Material.PURPLE_BED, 10);
		register(list, Material.BLUE_BED, 11);
		register(list, Material.BROWN_BED, 12);
		register(list, Material.GREEN_BED, 13);
		register(list, Material.RED_BED, 14);
		register(list, Material.BLACK_BED, 15);
	}

}