package protocolsupport.protocol.utils.types;

import protocolsupport.utils.CollectionsUtils;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public enum Environment {

	OVERWORLD(0), NETHER(-1), THE_END(1);

	private static final ArrayMap<Environment> byId = CollectionsUtils.makeEnumMappingArrayMap(Environment.class, Environment::getId);

	private final int id;
	Environment(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public static Environment getById(int id) {
		Environment env = byId.get(id);
		return env != null ? env : Environment.OVERWORLD;
	}

}
