package protocolsupport.protocol.packet.middlepacket.serverbound.play;

import net.minecraft.server.v1_9_R1.Packet;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middlepacket.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middlepacketimpl.PacketCreator;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddlePlayerAbilities extends ServerBoundMiddlePacket {

	protected int flags;
	protected float flySpeed;
	protected float walkSpeed;

	@Override
	public RecyclableCollection<? extends Packet<?>> toNative() throws Exception {
		PacketCreator creator = PacketCreator.create(ServerBoundPacket.PLAY_ABILITIES.get());
		creator.writeByte(flags);
		creator.writeFloat(flySpeed);
		creator.writeFloat(walkSpeed);
		return RecyclableSingletonList.create(creator.create());
	}

}
