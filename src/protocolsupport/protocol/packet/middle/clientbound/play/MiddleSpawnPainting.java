package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.types.Position;

public abstract class MiddleSpawnPainting extends ClientBoundMiddlePacket {

	protected int entityId;
	protected UUID uuid;
	protected String type;
	protected Position position = new Position(0, 0, 0);
	protected int direction;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		entityId = VarNumberSerializer.readVarInt(serverdata);
		uuid = MiscSerializer.readUUID(serverdata);
		type = StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC, 13);
		PositionSerializer.readPositionTo(serverdata, position);
		direction = serverdata.readUnsignedByte();
	}

}
