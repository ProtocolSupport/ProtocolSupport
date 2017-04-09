package protocolsupport.protocol.typeremapper.watchedentity.types;

public class WatchedPlayer extends WatchedEntity {

	public WatchedPlayer(int id) {
		super(id);
	}

	@Override
	public WatchedType getType() {
		return WatchedType.PLAYER;
	}

}
