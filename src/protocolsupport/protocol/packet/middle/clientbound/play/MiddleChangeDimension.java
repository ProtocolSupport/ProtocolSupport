package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.types.Difficulty;
import protocolsupport.protocol.utils.types.Environment;
import protocolsupport.protocol.utils.types.GameMode;

public abstract class MiddleChangeDimension extends ClientBoundMiddlePacket {

	public MiddleChangeDimension(ConnectionImpl connection) {
		super(connection);
	}

	protected Environment dimension;
	protected Difficulty difficulty;
	protected GameMode gamemode;
	protected String leveltype;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		dimension = Environment.getById(serverdata.readInt());
		difficulty = Difficulty.getById(serverdata.readByte());
		gamemode = GameMode.getById(serverdata.readByte());
		leveltype = StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC, 16);
	}

	@Override
	public boolean postFromServerRead() {
		cache.getWatchedEntityCache().clearWatchedEntities();
		cache.getAttributesCache().setCurrentDimension(dimension);
		return true;
	}

}
