package protocolsupport.protocol.packet.middle.serverbound.status;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddlePing extends ServerBoundMiddlePacket {

	public MiddlePing(ConnectionImpl connection) {
		super(connection);
	}

	protected long pingId;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.STATUS_PING);
		creator.writeLong(pingId);
		return RecyclableSingletonList.create(creator);
	}

}
