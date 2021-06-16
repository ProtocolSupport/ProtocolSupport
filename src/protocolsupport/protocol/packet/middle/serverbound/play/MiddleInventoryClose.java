package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.storage.netcache.window.WindowCache;

public abstract class MiddleInventoryClose extends ServerBoundMiddlePacket {

	protected final WindowCache windowCache = cache.getWindowCache();

	protected MiddleInventoryClose(MiddlePacketInit init) {
		super(init);
	}

	protected byte windowId;

	@Override
	protected void handle() {
		windowCache.closeWindow();
	}

	@Override
	protected void write() {
		codec.writeServerbound(create(windowId));
	}

	public static ServerBoundPacketData create(int windowId) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacketType.SERVERBOUND_PLAY_WINDOW_CLOSE);
		creator.writeByte(windowId);
		return creator;
	}

}
