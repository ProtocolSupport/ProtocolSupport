package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnPainting;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.UUIDSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class SpawnPainting extends MiddleSpawnPainting {

	public SpawnPainting(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData spawnpainting = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SPAWN_PAINTING);
		VarNumberSerializer.writeVarInt(spawnpainting, entity.getId());
		UUIDSerializer.writeUUID2L(spawnpainting, entity.getUUID());
		VarNumberSerializer.writeVarInt(spawnpainting, type);
		PositionSerializer.writeLegacyPositionL(spawnpainting, position);
		spawnpainting.writeByte(direction);
		codec.write(spawnpainting);
	}

}
