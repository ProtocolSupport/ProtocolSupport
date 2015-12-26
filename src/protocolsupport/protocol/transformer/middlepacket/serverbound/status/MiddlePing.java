package protocolsupport.protocol.transformer.middlepacket.serverbound.status;

import net.minecraft.server.v1_8_R3.Packet;
import protocolsupport.protocol.ServerBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.ServerBoundMiddlePacket;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketCreator;
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
