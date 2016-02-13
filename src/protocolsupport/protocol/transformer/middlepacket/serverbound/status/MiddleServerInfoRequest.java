package protocolsupport.protocol.transformer.middlepacket.serverbound.status;

import net.minecraft.server.v1_8_R3.Packet;
import protocolsupport.protocol.ServerBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.ServerBoundMiddlePacket;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketCreator;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleServerInfoRequest extends ServerBoundMiddlePacket {

	@Override
	public RecyclableCollection<? extends Packet<?>> toNative() throws Exception {
		return RecyclableSingletonList.create(PacketCreator.create(ServerBoundPacket.STATUS_REQUEST.get()).create());
	}

}
