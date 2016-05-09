package protocolsupport.protocol.packet.middlepacket.serverbound.status;

import net.minecraft.server.v1_9_R1.Packet;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middlepacket.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middlepacketimpl.PacketCreator;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddlePing extends ServerBoundMiddlePacket {

	protected long pingId;

	@Override
	public RecyclableCollection<? extends Packet<?>> toNative() throws Exception {
		PacketCreator creator = PacketCreator.create(ServerBoundPacket.STATUS_PING.get());
		creator.writeLong(pingId);
		return RecyclableSingletonList.create(creator.create());
	}

}
