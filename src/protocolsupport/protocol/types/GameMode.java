package protocolsupport.protocol.types;

import java.text.MessageFormat;

import protocolsupport.utils.CollectionsUtils;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public enum GameMode {

	NOT_SET(-1), SURVIVAL(0), CREATIVE(1), ADVENTURE(2), SPECTATOR(3);

	private static final ArrayMap<GameMode> byId = CollectionsUtils.makeEnumMappingArrayMap(GameMode.class, GameMode::getId);

	private final int id;
	GameMode(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public static GameMode getById(int id) {
		GameMode gm = byId.get(id);
		if (gm == null) {
			throw new IllegalArgumentException(MessageFormat.format("Unknown gamemode network id {0}", id));
		}
		return gm;
	}

}
