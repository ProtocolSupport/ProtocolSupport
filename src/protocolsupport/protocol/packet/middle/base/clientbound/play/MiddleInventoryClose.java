package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;
import protocolsupport.protocol.storage.netcache.window.WindowCache;

public abstract class MiddleInventoryClose extends ClientBoundMiddlePacket {

	protected final WindowCache windowCache = cache.getWindowCache();

	protected MiddleInventoryClose(IMiddlePacketInit init) {
		super(init);
	}

	protected byte windowId;

	@Override
	protected void decode(ByteBuf serverdata) {
		windowId = serverdata.readByte();
	}

	@Override
	protected void handle() {
		windowCache.closeWindow();
	}

}
