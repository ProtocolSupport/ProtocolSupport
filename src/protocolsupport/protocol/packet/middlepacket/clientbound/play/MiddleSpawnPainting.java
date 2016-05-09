package protocolsupport.protocol.packet.middlepacket.clientbound.play;

import java.io.IOException;
import java.util.UUID;

import net.minecraft.server.v1_9_R1.BlockPosition;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middlepacket.ClientBoundMiddlePacket;

public abstract class MiddleSpawnPainting<T> extends ClientBoundMiddlePacket<T> {

	protected int entityId;
	protected UUID uuid;
	protected String type;
	protected BlockPosition position;
	protected int direction;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) throws IOException {
		entityId = serializer.readVarInt();
		uuid = serializer.readUUID();
		type = serializer.readString(13);
		position = serializer.readPosition();
		direction = serializer.readUnsignedByte();
	}

}
