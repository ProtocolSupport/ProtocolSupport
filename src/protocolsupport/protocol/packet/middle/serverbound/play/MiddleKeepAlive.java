package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
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
	public RecyclableCollection<? extends IPacketData> toNative() {
		if (keepAliveId != -1) {
			return RecyclableSingletonList.create(create(keepAliveId));
		} else {
			return RecyclableEmptyList.get();
		}
	}

	public static ServerBoundPacketData create(long keepAliveId) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_KEEP_ALIVE);
		creator.writeLong(keepAliveId);
		return creator;
	}

}
