package protocolsupport.protocol.packet.middle.serverbound.status;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.PacketCreator;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleServerInfoRequest extends ServerBoundMiddlePacket {

	@Override
	public RecyclableCollection<PacketCreator> toNative() throws Exception {
		return RecyclableSingletonList.create(PacketCreator.create(ServerBoundPacket.STATUS_REQUEST));
	}

}
