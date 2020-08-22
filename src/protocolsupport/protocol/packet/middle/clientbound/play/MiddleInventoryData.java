package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.CancelMiddlePacketException;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.storage.netcache.window.WindowCache;

public abstract class MiddleInventoryData extends ClientBoundMiddlePacket {

	protected final WindowCache windowCache = cache.getWindowCache();

	public MiddleInventoryData(MiddlePacketInit init) {
		super(init);
	}

	protected byte windowId;
	protected int type;
	protected int value;

	@Override
	protected void readServerData(ByteBuf serverdata) {
		windowId = serverdata.readByte();
		type = serverdata.readShort();
		value = serverdata.readShort();

		if (!windowCache.isValidWindowId(windowId)) {
			throw CancelMiddlePacketException.INSTANCE;
		}
	}

}
