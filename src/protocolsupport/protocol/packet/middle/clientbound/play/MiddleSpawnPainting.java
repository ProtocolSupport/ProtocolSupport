package protocolsupport.protocol.packet.middle.clientbound.play;

import java.io.IOException;
import java.util.UUID;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.protocol.utils.types.Position;

public abstract class MiddleSpawnPainting<T> extends ClientBoundMiddlePacket<T> {

	protected int entityId;
	protected UUID uuid;
	protected String type;
	protected Position position;
	protected int direction;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		entityId = serializer.readVarInt();
		uuid = serializer.readUUID();
		type = serializer.readString(13);
		position = serializer.readPosition();
		direction = serializer.readUnsignedByte();
	}

}
