package protocolsupport.protocol.typeremapper.watchedentity.types;

import protocolsupport.protocol.typeremapper.watchedentity.remapper.SpecificType;


public class WatchedObject extends WatchedEntity {

	private final SpecificType stype;

	public WatchedObject(int id, int type) {
		super(id);
		stype = SpecificType.getObjectByTypeId(type);
	}

	@Override
	public SpecificType getType() {
		return stype;
	}

}
