package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.storage.netcache.window.WindowCache;

public abstract class MiddleInventoryClose extends ClientBoundMiddlePacket {

	protected final WindowCache windowCache = cache.getWindowCache();

	public MiddleInventoryClose(MiddlePacketInit init) {
		super(init);
	}

	protected byte windowId;

	@Override
	protected void readServerData(ByteBuf serverdata) {
		windowId = serverdata.readByte();
	}

	@Override
	protected void handleReadData() {
		windowCache.closeWindow();
	}

}
