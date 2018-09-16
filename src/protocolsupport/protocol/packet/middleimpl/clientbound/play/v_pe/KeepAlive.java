package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleKeepAlive;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.zplatform.ServerPlatform;

public class KeepAlive extends MiddleKeepAlive {

	public KeepAlive(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		connection.receivePacket(ServerPlatform.get().getPacketFactory().createInboundKeepAlivePacket(cache.getKeepAliveCache().tryConfirmKeepAlive(keepAliveId)));
		return RecyclableEmptyList.get();
	}

}