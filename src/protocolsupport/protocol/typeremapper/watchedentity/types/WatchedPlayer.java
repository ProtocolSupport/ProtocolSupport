package protocolsupport.protocol.typeremapper.watchedentity.types;

import protocolsupport.protocol.typeremapper.watchedentity.remapper.SpecificRemapper;


public class WatchedPlayer extends WatchedEntity {

	public WatchedPlayer(int id) {
		super(id);
	}

	@Override
	public SpecificRemapper getType() {
		return SpecificRemapper.PLAYER;
	}

}
