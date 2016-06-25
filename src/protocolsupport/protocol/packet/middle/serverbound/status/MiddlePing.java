package protocolsupport.protocol.packet.middle.serverbound.status;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.PacketCreator;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddlePing extends ServerBoundMiddlePacket {

	protected long pingId;

	@Override
	public RecyclableCollection<PacketCreator> toNative() throws Exception {
		PacketCreator creator = PacketCreator.create(ServerBoundPacket.STATUS_PING);
		creator.writeLong(pingId);
		return RecyclableSingletonList.create(creator);
	}

}
