package protocolsupport.protocol.packet.middlepacket.serverbound.status;

import net.minecraft.server.v1_9_R1.Packet;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middlepacket.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middlepacketimpl.PacketCreator;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleServerInfoRequest extends ServerBoundMiddlePacket {

	@Override
	public RecyclableCollection<? extends Packet<?>> toNative() throws Exception {
		return RecyclableSingletonList.create(PacketCreator.create(ServerBoundPacket.STATUS_REQUEST.get()).create());
	}

}
