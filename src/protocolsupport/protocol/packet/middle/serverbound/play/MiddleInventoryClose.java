package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.storage.netcache.window.WindowCache;

public abstract class MiddleInventoryClose extends ServerBoundMiddlePacket {

	protected final WindowCache windowCache = cache.getWindowCache();

	public MiddleInventoryClose(ConnectionImpl connection) {
		super(connection);
	}

	protected byte windowId;

	@Override
	public void writeToServer() {
		windowCache.closeWindow();

		codec.read(create(windowId));
	}

	public static ServerBoundPacketData create(int windowId) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_WINDOW_CLOSE);
		creator.writeByte(windowId);
		return creator;
	}

}
