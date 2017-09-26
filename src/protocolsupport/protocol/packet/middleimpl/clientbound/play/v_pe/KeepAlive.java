package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleKeepAlive;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.zplatform.ServerPlatform;

public class KeepAlive extends MiddleKeepAlive {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		connection.receivePacket(ServerPlatform.get().getPacketFactory().createInboundKeepAlivePacket(cache.tryConfirmKeepAlive(keepAliveId)));
		return RecyclableEmptyList.get();
	}

}