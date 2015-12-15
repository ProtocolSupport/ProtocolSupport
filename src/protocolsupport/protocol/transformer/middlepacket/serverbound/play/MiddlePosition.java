package protocolsupport.protocol.transformer.middlepacket.serverbound.play;

import java.util.Collection;
import java.util.Collections;

import net.minecraft.server.v1_8_R3.Packet;

import protocolsupport.protocol.ServerBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.ServerBoundMiddlePacket;
import protocolsupport.utils.PacketCreator;

public abstract class MiddlePosition extends ServerBoundMiddlePacket {

	protected double x;
	protected double y;
	protected double z;
	protected boolean onGround;

	@Override
	public Collection<Packet<?>> toNative() throws Exception {
		PacketCreator creator = new PacketCreator(ServerBoundPacket.PLAY_POSITION.get());
		creator.writeDouble(x);
		creator.writeDouble(y);
		creator.writeDouble(z);
		creator.writeBoolean(onGround);
		return Collections.<Packet<?>>singletonList(creator.create());
	}

}
