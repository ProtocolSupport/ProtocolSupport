package protocolsupport.protocol.typeremapper.tile;

import java.util.List;
import java.util.function.Consumer;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Rotatable;

import protocolsupport.api.MaterialAPI;
import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper.TileEntityWithBlockDataNBTRemapper;
import protocolsupport.protocol.utils.types.nbt.NBTByte;
import protocolsupport.protocol.utils.types.nbt.NBTCompound;
import protocolsupport.utils.CollectionsUtils.ArrayMap.Entry;

public class TileEntitySkullRemapper extends TileEntityWithBlockDataNBTRemapper {

	protected static byte getLegacyRotation(BlockData skull) {
		if (skull instanceof Rotatable) {
			BlockFace face = ((Rotatable) skull).getRotation();
			switch (face) {
				case SOUTH: return 0;
				case SOUTH_SOUTH_WEST: return 1;
				case SOUTH_WEST: return 2;
				case WEST_SOUTH_WEST: return 3;
				case WEST: return 4;
				case WEST_NORTH_WEST: return 5;
				case NORTH_WEST: return 6;
				case NORTH_NORTH_WEST: return 7;
				case NORTH: return 8;
				case NORTH_NORTH_EAST: return 9;
				case NORTH_EAST: return 10;
				case EAST_NORTH_EAST: return 11;
				case EAST: return 12;
				case EAST_SOUTH_EAST: return 13;
				case SOUTH_EAST: return 14;
				case SOUTH_SOUTH_EAST: return 15;
				default: break;
			}
		}
		return 0;
	}

	protected void register(List<Entry<Consumer<NBTCompound>>> list, Material skull, int skulltype) {
		for (BlockData blockdata : MaterialAPI.getBlockDataList(skull)) {
			byte rotation = getLegacyRotation(blockdata);
			list.add(new Entry<>(MaterialAPI.getBlockDataNetworkId(blockdata), nbt -> {
				nbt.setTag("SkullType", new NBTByte((byte) skulltype));
				nbt.setTag("Rot", new NBTByte(rotation));
			}));
		}
	}

	@Override
	protected void init(List<Entry<Consumer<NBTCompound>>> list) {
		register(list, Material.SKELETON_SKULL, 0);
		register(list, Material.WITHER_SKELETON_SKULL, 1);
		register(list, Material.ZOMBIE_HEAD, 2);
		register(list, Material.PLAYER_HEAD, 3);
		register(list, Material.CREEPER_HEAD, 4);
		register(list, Material.DRAGON_HEAD, 5);
		register(list, Material.SKELETON_WALL_SKULL, 0);
		register(list, Material.WITHER_SKELETON_WALL_SKULL, 1);
		register(list, Material.ZOMBIE_WALL_HEAD, 2);
		register(list, Material.PLAYER_WALL_HEAD, 3);
		register(list, Material.CREEPER_WALL_HEAD, 4);
		register(list, Material.DRAGON_WALL_HEAD, 5);
	}

}
