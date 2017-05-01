package protocolsupport.protocol.typeremapper.watchedentity.types;

public class WatchedObject extends WatchedEntity {

	private final WatchedType stype;

	public WatchedObject(int id, int type, int objectData) {
		super(id);
		stype = WatchedType.getObjectByTypeAndData(type, objectData);
	}

	public WatchedObject(int id, int type){
		this(id, type, 0);
	}

	@Override
	public WatchedType getType() {
		return stype;
	}

}
