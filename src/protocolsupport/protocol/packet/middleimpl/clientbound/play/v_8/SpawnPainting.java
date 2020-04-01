package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnPainting;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyPainting;

public class SpawnPainting extends MiddleSpawnPainting {

	public SpawnPainting(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData spawnpainting = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SPAWN_PAINTING);
		VarNumberSerializer.writeVarInt(spawnpainting, entity.getId());
		StringSerializer.writeVarIntUTF8String(spawnpainting, LegacyPainting.getName(type));
		PositionSerializer.writeLegacyPositionL(spawnpainting, position);
		spawnpainting.writeByte(direction);
		codec.write(spawnpainting);
	}

}
