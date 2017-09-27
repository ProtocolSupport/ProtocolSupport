package protocolsupport.protocol.packet.middleimpl.clientbound.login.noop;

import protocolsupport.protocol.packet.middle.clientbound.login.MiddleSetCompression;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;

public class NoopSetCompression extends MiddleSetCompression {

	@Override
	public boolean postFromServerRead() {
		return false;
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		return RecyclableEmptyList.get();
	}

}
