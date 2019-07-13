package protocolsupport.protocol.typeremapper.tile;

import java.util.List;
import java.util.function.Consumer;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

import protocolsupport.api.MaterialAPI;
import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper.TileEntityWithBlockDataNBTRemapper;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTInt;
import protocolsupport.protocol.types.nbt.NBTList;
import protocolsupport.protocol.types.nbt.NBTType;
import protocolsupport.utils.CollectionsUtils.ArrayMap;
import protocolsupport.utils.CollectionsUtils.ArrayMap.Entry;

public class TileEntityBannerRemapper extends TileEntityWithBlockDataNBTRemapper {

	protected void register(List<Entry<Consumer<NBTCompound>>> list, Material banner, int color) {
		for (BlockData blockdata : MaterialAPI.getBlockDataList(banner)) {
			list.add(new ArrayMap.Entry<>(MaterialAPI.getBlockDataNetworkId(blockdata), nbt -> {
				nbt.setTag("Base", new NBTInt(color));
				NBTList<NBTCompound> patterns = nbt.getTagListOfType("Patterns", NBTType.COMPOUND);
				if (patterns != null) {
					for (NBTCompound pattern : patterns.getTags()) {
						pattern.setTag("Color", new NBTInt(15 - pattern.getNumberTag("Color").getAsInt()));
					}
				}
			}));
		}
	}

	@Override
	protected void init(List<Entry<Consumer<NBTCompound>>> list) {
		register(list, Material.WHITE_BANNER, 15);
		register(list, Material.ORANGE_BANNER, 14);
		register(list, Material.MAGENTA_BANNER, 13);
		register(list, Material.LIGHT_BLUE_BANNER, 12);
		register(list, Material.YELLOW_BANNER, 11);
		register(list, Material.LIME_BANNER, 10);
		register(list, Material.PINK_BANNER, 9);
		register(list, Material.GRAY_BANNER, 8);
		register(list, Material.LIGHT_GRAY_BANNER, 7);
		register(list, Material.CYAN_BANNER, 6);
		register(list, Material.PURPLE_BANNER, 5);
		register(list, Material.BLUE_BANNER, 4);
		register(list, Material.BROWN_BANNER, 3);
		register(list, Material.GREEN_BANNER, 2);
		register(list, Material.RED_BANNER, 1);
		register(list, Material.BLACK_BANNER, 0);
		register(list, Material.WHITE_WALL_BANNER, 15);
		register(list, Material.ORANGE_WALL_BANNER, 14);
		register(list, Material.MAGENTA_WALL_BANNER, 13);
		register(list, Material.LIGHT_BLUE_WALL_BANNER, 12);
		register(list, Material.YELLOW_WALL_BANNER, 11);
		register(list, Material.LIME_WALL_BANNER, 10);
		register(list, Material.PINK_WALL_BANNER, 9);
		register(list, Material.GRAY_WALL_BANNER, 8);
		register(list, Material.LIGHT_GRAY_WALL_BANNER, 7);
		register(list, Material.CYAN_WALL_BANNER, 6);
		register(list, Material.PURPLE_WALL_BANNER, 5);
		register(list, Material.BLUE_WALL_BANNER, 4);
		register(list, Material.BROWN_WALL_BANNER, 3);
		register(list, Material.GREEN_WALL_BANNER, 2);
		register(list, Material.RED_WALL_BANNER, 1);
		register(list, Material.BLACK_WALL_BANNER, 0);
	}
}