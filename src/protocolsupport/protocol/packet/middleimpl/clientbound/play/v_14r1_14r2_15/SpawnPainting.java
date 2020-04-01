package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2_15;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnPainting;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class SpawnPainting extends MiddleSpawnPainting {

	public SpawnPainting(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData spawnpainting = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SPAWN_PAINTING);
		VarNumberSerializer.writeVarInt(spawnpainting, entity.getId());
		MiscSerializer.writeUUID(spawnpainting, entity.getUUID());
		VarNumberSerializer.writeVarInt(spawnpainting, type);
		PositionSerializer.writePosition(spawnpainting, position);
		spawnpainting.writeByte(direction);
		codec.write(spawnpainting);
	}

}
