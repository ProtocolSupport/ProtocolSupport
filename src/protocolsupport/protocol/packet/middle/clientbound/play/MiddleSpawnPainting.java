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
import protocolsupport.protocol.utils.types.networkentity.NetworkEntity;
import protocolsupport.protocol.utils.types.networkentity.NetworkEntityType;

public abstract class MiddleSpawnPainting extends ClientBoundMiddlePacket {

	protected NetworkEntity entity;
	protected String type;
	protected Position position = new Position(0, 0, 0);
	protected int direction;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		int entityId = VarNumberSerializer.readVarInt(serverdata);
		UUID uuid = MiscSerializer.readUUID(serverdata);
		entity = new NetworkEntity(uuid, entityId, NetworkEntityType.PAINTING);
		type = StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC, 13);
		PositionSerializer.readPositionTo(serverdata, position);
		direction = serverdata.readUnsignedByte();
	}

}
