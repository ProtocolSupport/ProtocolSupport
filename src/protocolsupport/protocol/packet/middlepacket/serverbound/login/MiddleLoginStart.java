package protocolsupport.protocol.packet.middlepacket.serverbound.login;

import net.minecraft.server.v1_9_R1.Packet;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middlepacket.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middlepacketimpl.PacketCreator;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleLoginStart extends ServerBoundMiddlePacket {

	protected String name;

	@Override
	public RecyclableCollection<? extends Packet<?>> toNative() throws Exception {
		PacketCreator creator = PacketCreator.create(ServerBoundPacket.LOGIN_START.get());
		creator.writeString(name);
		return RecyclableSingletonList.create(creator.create());
	}

}
