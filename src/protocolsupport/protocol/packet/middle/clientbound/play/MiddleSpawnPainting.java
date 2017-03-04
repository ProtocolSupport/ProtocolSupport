package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.types.Position;

public abstract class MiddleSpawnPainting extends ClientBoundMiddlePacket {

	protected int entityId;
	protected UUID uuid;
	protected String type;
	protected Position position;
	protected int direction;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		entityId = VarNumberSerializer.readVarInt(serverdata);
		uuid = MiscSerializer.readUUID(serverdata);
		type = StringSerializer.readString(serverdata, ProtocolVersion.getLatest(), 13);
		position = PositionSerializer.readPosition(serverdata);
		direction = serverdata.readUnsignedByte();
	}

}
