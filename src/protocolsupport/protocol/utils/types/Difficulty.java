package protocolsupport.protocol.utils.types;

import java.text.MessageFormat;

import protocolsupport.utils.CollectionsUtils;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public enum Difficulty {

	PEACEFUL(0), EASY(1), NORMAL(2), HARD(3);

	private static final ArrayMap<Difficulty> byId = CollectionsUtils.makeEnumMappingArrayMap(Difficulty.class, Difficulty::getId);

	private final int id;
	Difficulty(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public static Difficulty getById(int id) {
		Difficulty diff = byId.get(id);
		if (diff == null) {
			throw new IllegalArgumentException(MessageFormat.format("Unknown difficulty network id {0}", id));
		}
		return diff;
	}

}
