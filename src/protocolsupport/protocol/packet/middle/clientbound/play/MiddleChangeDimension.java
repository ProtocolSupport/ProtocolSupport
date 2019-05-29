package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.types.Environment;
import protocolsupport.protocol.types.GameMode;

public abstract class MiddleChangeDimension extends ClientBoundMiddlePacket {

	public MiddleChangeDimension(ConnectionImpl connection) {
		super(connection);
	}

	protected Environment dimension;
	protected GameMode gamemode;
	protected String leveltype;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		dimension = Environment.getById(serverdata.readInt());
		gamemode = GameMode.getById(serverdata.readByte());
		leveltype = StringSerializer.readVarIntUTF8String(serverdata);
	}

	@Override
	public boolean postFromServerRead() {
		cache.getWatchedEntityCache().clearWatchedEntities();
		cache.getAttributesCache().setCurrentDimension(dimension);
		return true;
	}

}
