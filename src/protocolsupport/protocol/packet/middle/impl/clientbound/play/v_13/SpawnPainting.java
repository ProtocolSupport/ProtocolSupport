package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_13;

import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.UUIDCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleSpawnPainting;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV13;

public class SpawnPainting extends MiddleSpawnPainting implements IClientboundMiddlePacketV13 {

	public SpawnPainting(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData spawnpainting = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SPAWN_PAINTING);
		VarNumberCodec.writeVarInt(spawnpainting, entity.getId());
		UUIDCodec.writeUUID2L(spawnpainting, entity.getUUID());
		VarNumberCodec.writeVarInt(spawnpainting, type);
		PositionCodec.writePositionLXYZ(spawnpainting, position);
		spawnpainting.writeByte(direction);
		io.writeClientbound(spawnpainting);
	}

}
