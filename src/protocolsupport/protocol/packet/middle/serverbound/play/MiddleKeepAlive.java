package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleKeepAlive extends ServerBoundMiddlePacket {

	public MiddleKeepAlive(ConnectionImpl connection) {
		super(connection);
	}

	protected long keepAliveId;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		if (keepAliveId != -1) {
			ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_KEEP_ALIVE);
			creator.writeLong(keepAliveId);
			return RecyclableSingletonList.create(creator);
		} else {
			return RecyclableEmptyList.get();
		}
	}

}
