package protocolsupport.protocol.packet.middleimpl.clientbound.login.noop;

import protocolsupport.protocol.packet.middle.clientbound.login.MiddleLoginCustomPayload;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;

public class NoopLoginCustomPayload extends MiddleLoginCustomPayload {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		return RecyclableEmptyList.get();
	}

}
