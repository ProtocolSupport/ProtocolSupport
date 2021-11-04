package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_8;

import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleSpawnPainting;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV8;
import protocolsupport.protocol.typeremapper.legacy.LegacyPainting;

public class SpawnPainting extends MiddleSpawnPainting implements IClientboundMiddlePacketV8 {

	public SpawnPainting(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData spawnpainting = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SPAWN_PAINTING);
		VarNumberCodec.writeVarInt(spawnpainting, entity.getId());
		StringCodec.writeVarIntUTF8String(spawnpainting, LegacyPainting.getName(type));
		PositionCodec.writePositionLXYZ(spawnpainting, position);
		spawnpainting.writeByte(direction);
		io.writeClientbound(spawnpainting);
	}

}
