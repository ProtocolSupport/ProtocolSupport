package protocolsupport.protocol.packet.middlepacket.clientbound.play;

import java.io.IOException;

import net.minecraft.server.v1_9_R1.BlockPosition;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middlepacket.ClientBoundMiddlePacket;

public abstract class MiddleUseBed<T> extends ClientBoundMiddlePacket<T> {

	protected int entityId;
	protected BlockPosition bed;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) throws IOException {
		entityId = serializer.readVarInt();
		bed = serializer.readPosition();
	}

	@Override
	public void handle() {
	}

}
