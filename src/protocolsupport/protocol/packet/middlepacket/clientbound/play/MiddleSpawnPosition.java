package protocolsupport.protocol.packet.middlepacket.clientbound.play;

import java.io.IOException;

import net.minecraft.server.v1_9_R1.BlockPosition;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middlepacket.ClientBoundMiddlePacket;

public abstract class MiddleSpawnPosition<T> extends ClientBoundMiddlePacket<T> {

	protected BlockPosition position;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) throws IOException {
		position = serializer.readPosition();
	}

}
