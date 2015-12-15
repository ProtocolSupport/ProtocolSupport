package protocolsupport.protocol.transformer.middlepacket.serverbound.play;

import java.util.Collection;
import java.util.Collections;

import net.minecraft.server.v1_8_R3.Packet;

import protocolsupport.protocol.ServerBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.ServerBoundMiddlePacket;
import protocolsupport.utils.PacketCreator;

public abstract class MiddleKeepAlive extends ServerBoundMiddlePacket {

	protected int keepAliveId;

	@Override
	public Collection<Packet<?>> toNative() throws Exception {
		PacketCreator creator = new PacketCreator(ServerBoundPacket.PLAY_KEEP_ALIVE.get());
		creator.writeVarInt(keepAliveId);
		return Collections.<Packet<?>>singletonList(creator.create());
	}

}
