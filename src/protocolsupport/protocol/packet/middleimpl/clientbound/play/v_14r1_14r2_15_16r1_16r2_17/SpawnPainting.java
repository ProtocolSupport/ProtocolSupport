package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2_15_16r1_16r2_17;

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
	protected void write() {
		ClientBoundPacketData spawnpainting = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SPAWN_PAINTING);
		VarNumberSerializer.writeVarInt(spawnpainting, entity.getId());
		UUIDSerializer.writeUUID2L(spawnpainting, entity.getUUID());
		VarNumberSerializer.writeVarInt(spawnpainting, type);
		PositionSerializer.writePosition(spawnpainting, position);
		spawnpainting.writeByte(direction);
		codec.writeClientbound(spawnpainting);
	}

}
