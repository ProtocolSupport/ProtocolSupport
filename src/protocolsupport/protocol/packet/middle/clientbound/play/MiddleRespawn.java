package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleRespawn<T> extends ClientBoundMiddlePacket<T> {

	protected int dimension;
	protected int difficulty;
	protected int gamemode;
	protected String leveltype;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		dimension = serializer.readInt();
		difficulty = serializer.readByte();
		gamemode = serializer.readByte();
		leveltype = serializer.readString(16);
	}

	@Override
	public void handle() {
		storage.clearWatchedEntities();
		storage.setDimensionId(dimension);
	}

}
