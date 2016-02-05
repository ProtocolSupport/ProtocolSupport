package protocolsupport.protocol.transformer.middlepacket.clientbound.play;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.ClientBoundMiddlePacket;

public abstract class MiddleRespawn<T> extends ClientBoundMiddlePacket<T> {

	protected int dimension;
	protected int difficulty;
	protected int gamemode;
	protected String leveltype;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) {
		dimension = serializer.readInt();
		difficulty = serializer.readByte();
		gamemode = serializer.readByte();
		leveltype = serializer.readString(16);
	}

	@Override
	public void handle() {
		storage.clearWatchedEntities();
	}

}
