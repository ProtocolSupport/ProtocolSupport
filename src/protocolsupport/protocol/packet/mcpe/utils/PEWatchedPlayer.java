package protocolsupport.protocol.packet.mcpe.utils;

import java.util.UUID;

import protocolsupport.protocol.typeremapper.watchedentity.types.WatchedPlayer;

public class PEWatchedPlayer extends WatchedPlayer {

	private final UUID uuid;
	public PEWatchedPlayer(int id, UUID uuid) {
		super(id);
		this.uuid = uuid;
	}

	public UUID getUUID() {
		return uuid;
	}

}
