package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.storage.netcache.window.WindowCache;

public abstract class MiddleInventoryData extends ClientBoundMiddlePacket {

	protected final WindowCache windowCache = cache.getWindowCache();

	public MiddleInventoryData(ConnectionImpl connection) {
		super(connection);
	}

	protected byte windowId;
	protected int type;
	protected int value;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		windowId = serverdata.readByte();
		type = serverdata.readShort();
		value = serverdata.readShort();
	}

	@Override
	public boolean postFromServerRead() {
		return windowCache.isValidWindowId(windowId);
	}

}
