package protocolsupport.protocol.typeremapper.watchedentity.types;

import protocolsupport.protocol.typeremapper.watchedentity.remapper.SpecificRemapper;


public class WatchedLiving extends WatchedEntity {

	private final SpecificRemapper stype;

	public WatchedLiving(int id, int type) {
		super(id);
		stype = SpecificRemapper.getMobByTypeId(type);
	}

	@Override
	public SpecificRemapper getType() {
		return stype;
	}

}
