package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnPainting;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyPainting;

public class SpawnPainting extends MiddleSpawnPainting {

	public SpawnPainting(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
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
		ClientBoundPacketData spawnpainting = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SPAWN_PAINTING);
		spawnpainting.writeInt(entity.getId());
		StringSerializer.writeShortUTF16BEString(spawnpainting, LegacyPainting.getName(type));
		PositionSerializer.writeLegacyPositionI(spawnpainting, position);
		spawnpainting.writeInt(direction);
		codec.write(spawnpainting);
	}

}
