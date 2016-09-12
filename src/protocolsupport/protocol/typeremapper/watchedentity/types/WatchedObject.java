package protocolsupport.protocol.typeremapper.watchedentity.types;

import protocolsupport.protocol.typeremapper.watchedentity.remapper.SpecificRemapper;


public class WatchedObject extends WatchedEntity {

	private final SpecificRemapper stype;

	public WatchedObject(int id, int type) {
		super(id);
		stype = SpecificRemapper.getObjectByTypeId(type);
	}

	@Override
	public SpecificRemapper getType() {
		return stype;
	}

}
