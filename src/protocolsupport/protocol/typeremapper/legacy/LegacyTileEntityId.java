package protocolsupport.protocol.typeremapper.legacy;

import java.util.EnumMap;

import protocolsupport.protocol.types.TileEntityType;

public class LegacyTileEntityId {

	private static final EnumMap<TileEntityType, Integer> toLegacyId = new EnumMap<>(TileEntityType.class);

	private static void register(TileEntityType type, int id) {
		toLegacyId.put(type, id);
	}

	static {
		register(TileEntityType.MOB_SPAWNER, 1);
		register(TileEntityType.COMMAND_BLOCK, 2);
		register(TileEntityType.BEACON, 3);
		register(TileEntityType.SKULL, 4);
		register(TileEntityType.CONDUIT, 5);
		register(TileEntityType.BANNER, 6);
		register(TileEntityType.STRUCTURE, 7);
		register(TileEntityType.END_GATEWAY, 8);
		register(TileEntityType.SIGN, 9);
		register(TileEntityType.SHULKER_BOX, 10);
		register(TileEntityType.BED, 11);
		register(TileEntityType.JIGSAW, 12);
		register(TileEntityType.CAMPFIRE, 13);
		register(TileEntityType.BEEHIVE, 14);
	}

	public static int toLegacyId(TileEntityType type) {
		return toLegacyId.getOrDefault(type, 0);
	}

}
