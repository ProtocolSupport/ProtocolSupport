package protocolsupport.protocol.typeremapper.watchedentity.types;

import protocolsupport.protocol.typeremapper.watchedentity.remapper.SpecificType;


public class WatchedLiving extends WatchedEntity {

	private final SpecificType stype;

	public WatchedLiving(int id, int type) {
		super(id);
		stype = SpecificType.getMobByTypeId(type);
	}

	@Override
	public SpecificType getType() {
		return stype;
	}

}
