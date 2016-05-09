package protocolsupport.protocol.packet.middlepacket.serverbound.play;

import java.util.UUID;

import net.minecraft.server.v1_9_R1.Packet;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middlepacket.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middlepacketimpl.PacketCreator;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleSpecate extends ServerBoundMiddlePacket {

	protected UUID entityUUID;

	@Override
	public RecyclableCollection<? extends Packet<?>> toNative() throws Exception {
		PacketCreator creator = PacketCreator.create(ServerBoundPacket.PLAY_SPECTATE.get());
		creator.writeUUID(entityUUID);
		return RecyclableSingletonList.create(creator.create());
	}

}
