package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2_10_11_12r1_12r2;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnPainting;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.UUIDSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyPainting;

public class SpawnPainting extends MiddleSpawnPainting {

	public SpawnPainting(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData spawnpainting = ClientBoundPacketData.create(ClientBoundPacketType.CLIENTBOUND_PLAY_SPAWN_PAINTING);
		VarNumberSerializer.writeVarInt(spawnpainting, entity.getId());
		UUIDSerializer.writeUUID2L(spawnpainting, entity.getUUID());
		StringSerializer.writeVarIntUTF8String(spawnpainting, LegacyPainting.getName(type));
		PositionSerializer.writeLegacyPositionL(spawnpainting, position);
		spawnpainting.writeByte(direction);
		codec.writeClientbound(spawnpainting);
	}

}
