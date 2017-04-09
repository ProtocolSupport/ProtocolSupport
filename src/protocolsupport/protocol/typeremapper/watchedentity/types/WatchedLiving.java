package protocolsupport.protocol.typeremapper.watchedentity.types;

public class WatchedLiving extends WatchedEntity {

	private final WatchedType stype;

	public WatchedLiving(int id, int type) {
		super(id);
		stype = WatchedType.getMobByTypeId(type);
	}

	@Override
	public WatchedType getType() {
		return stype;
	}

}
