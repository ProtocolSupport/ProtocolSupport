package protocolsupport.protocol.transformer.middlepacket.serverbound.play;

import java.util.Collection;
import java.util.Collections;

import net.minecraft.server.v1_8_R3.Packet;
import protocolsupport.protocol.ServerBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.ServerBoundMiddlePacket;
import protocolsupport.utils.PacketCreator;

public abstract class MiddleAnimation extends ServerBoundMiddlePacket {

	@Override
	public Collection<Packet<?>> toNative() throws Exception {
		return Collections.<Packet<?>>singletonList(new PacketCreator(ServerBoundPacket.PLAY_ANIMATION.get()).create());
	}

}
