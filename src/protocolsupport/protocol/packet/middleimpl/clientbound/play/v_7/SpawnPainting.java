package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnPainting;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyPainting;

public class SpawnPainting extends MiddleSpawnPainting {

	public SpawnPainting(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		switch (direction) {
			case 0: {
				position.modifyZ(-1);
				break;
			}
			case 1: {
				position.modifyX(1);
				break;
			}
			case 2: {
				position.modifyZ(1);
				break;
			}
			case 3: {
				position.modifyX(-1);
				break;
			}
		}
		ClientBoundPacketData spawnpainting = ClientBoundPacketData.create(ClientBoundPacketType.CLIENTBOUND_PLAY_SPAWN_PAINTING);
		VarNumberSerializer.writeVarInt(spawnpainting, entity.getId());
		StringSerializer.writeVarIntUTF8String(spawnpainting, LegacyPainting.getName(type));
		PositionSerializer.writeLegacyPositionI(spawnpainting, position);
		spawnpainting.writeInt(direction);
		codec.writeClientbound(spawnpainting);
	}

}
