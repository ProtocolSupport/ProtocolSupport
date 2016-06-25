package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.api.tab.TabAPI;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.protocol.typeremapper.watchedentity.types.WatchedPlayer;

public abstract class MiddleLogin<T> extends ClientBoundMiddlePacket<T> {

	protected int playerEntityId;
	protected int gamemode;
	protected int dimension;
	protected int difficulty;
	protected int maxplayers;
	protected String leveltype;
	protected boolean reducedDebugInfo;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		playerEntityId = serializer.readInt();
		gamemode = serializer.readByte();
		dimension = serializer.readInt();
		difficulty = serializer.readByte();
		serializer.readByte();
		maxplayers = TabAPI.getMaxTabSize();
		leveltype = serializer.readString(16);
		reducedDebugInfo = serializer.readBoolean();
	}

	@Override
	public void handle() {
		storage.addWatchedSelfPlayer(new WatchedPlayer(playerEntityId));
		storage.setDimensionId(dimension);
	}

}
