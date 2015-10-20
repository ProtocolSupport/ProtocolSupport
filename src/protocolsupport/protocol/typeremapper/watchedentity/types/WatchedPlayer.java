package protocolsupport.protocol.typeremapper.watchedentity.types;

import protocolsupport.protocol.typeremapper.watchedentity.remapper.SpecificType;


public class WatchedPlayer extends WatchedEntity {

	public WatchedPlayer(int id) {
		super(id);
	}

	@Override
	public SpecificType getType() {
		return SpecificType.PLAYER;
	}

}
