package protocolsupport.protocol.typeremapper.watchedentity.types;



public class WatchedObject extends WatchedEntity {

	private final WatchedType stype;

	public WatchedObject(int id, int type) {
		super(id);
		stype = WatchedType.getObjectByTypeId(type);
	}

	@Override
	public WatchedType getType() {
		return stype;
	}

}
