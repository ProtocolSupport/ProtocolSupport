package protocolsupport.protocol.packet.middleimpl.clientbound.play.noop;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityLeash;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;

public class NoopEntityLeash extends MiddleEntityLeash {

	@Override
	public boolean postFromServerRead() {
		return false;
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		return RecyclableEmptyList.get();
	}

}
