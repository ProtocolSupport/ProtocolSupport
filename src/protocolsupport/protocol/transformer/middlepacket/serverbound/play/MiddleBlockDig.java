package protocolsupport.protocol.transformer.middlepacket.serverbound.play;

import java.util.Collection;
import java.util.Collections;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Packet;
import protocolsupport.protocol.ServerBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.ServerBoundMiddlePacket;
import protocolsupport.utils.PacketCreator;

public abstract class MiddleBlockDig extends ServerBoundMiddlePacket {

	protected int status;
	protected BlockPosition position;
	protected int face;

	@Override
	public Collection<Packet<?>> toNative() throws Exception {
		PacketCreator creator = new PacketCreator(ServerBoundPacket.PLAY_BLOCK_DIG.get());
		creator.writeByte(status);
		creator.writePosition(position);
		creator.writeByte(face);
		return Collections.<Packet<?>>singletonList(creator.create());
	}

}
