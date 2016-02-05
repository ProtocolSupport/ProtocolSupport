package protocolsupport.protocol.transformer.middlepacket.clientbound.play;

import protocolsupport.api.tab.TabAPI;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.ClientBoundMiddlePacket;
import protocolsupport.protocol.typeremapper.watchedentity.types.WatchedPlayer;

public abstract class MiddleLogin<T> extends ClientBoundMiddlePacket<T> {

	protected int playerEntityId;
	protected int gamemode;
	protected int dimension;
	protected int difficulty;
	protected int maxplayers;
	protected String leveltype;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) {
		playerEntityId = serializer.readInt();
		gamemode = serializer.readByte();
		dimension = serializer.readByte();
		difficulty = serializer.readByte();
		serializer.readByte();
		maxplayers = TabAPI.getMaxTabSize();
		leveltype = serializer.readString(16);
	}

	@Override
	public void handle() {
		storage.addWatchedSelfPlayer(new WatchedPlayer(playerEntityId));
	}

}
