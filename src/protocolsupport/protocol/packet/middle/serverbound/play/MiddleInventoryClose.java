package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleInventoryClose extends ServerBoundMiddlePacket {

	public MiddleInventoryClose(ConnectionImpl connection) {
		super(connection);
	}

	protected int windowId;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		cache.getWindowCache().closeWindow();
		return RecyclableSingletonList.create(create(windowId));
	}

	public static ServerBoundPacketData create(int windowId) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_WINDOW_CLOSE);
		creator.writeByte(windowId);
		return creator;
	}

}
