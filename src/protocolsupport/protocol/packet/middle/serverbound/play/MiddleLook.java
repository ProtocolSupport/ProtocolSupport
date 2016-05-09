package protocolsupport.protocol.packet.middle.serverbound.play;

import net.minecraft.server.v1_9_R1.Packet;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.PacketCreator;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleLook extends ServerBoundMiddlePacket {

	protected float yaw;
	protected float pitch;
	protected boolean onGround;

	@Override
	public RecyclableCollection<? extends Packet<?>> toNative() throws Exception {
		PacketCreator creator = PacketCreator.create(ServerBoundPacket.PLAY_LOOK.get());
		creator.writeFloat(yaw);
		creator.writeFloat(pitch);
		creator.writeBoolean(onGround);
		return RecyclableSingletonList.create(creator.create());
	}

}
