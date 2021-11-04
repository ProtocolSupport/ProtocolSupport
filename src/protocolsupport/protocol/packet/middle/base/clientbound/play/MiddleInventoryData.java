package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.MiddlePacketCancelException;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;
import protocolsupport.protocol.storage.netcache.window.WindowCache;

public abstract class MiddleInventoryData extends ClientBoundMiddlePacket {

	protected final WindowCache windowCache = cache.getWindowCache();

	protected MiddleInventoryData(IMiddlePacketInit init) {
		super(init);
	}

	protected byte windowId;
	protected int type;
	protected int value;

	@Override
	protected void decode(ByteBuf serverdata) {
		windowId = serverdata.readByte();
		type = serverdata.readShort();
		value = serverdata.readShort();

		if (!windowCache.isValidWindowId(windowId)) {
			throw MiddlePacketCancelException.INSTANCE;
		}
	}

}
