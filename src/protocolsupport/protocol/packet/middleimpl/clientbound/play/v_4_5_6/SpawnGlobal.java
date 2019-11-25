package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnGlobal;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class SpawnGlobal extends MiddleSpawnGlobal {

	public SpawnGlobal(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData spawnglobal = codec.allocClientBoundPacketData(PacketType.CLIENTBOUND_PLAY_SPAWN_GLOBAL);
		spawnglobal.writeInt(entity.getId());
		spawnglobal.writeByte(entity.getType().getNetworkTypeId());
		spawnglobal.writeInt((int) (x * 32));
		spawnglobal.writeInt((int) (y * 32));
		spawnglobal.writeInt((int) (z * 32));
		codec.write(spawnglobal);
	}

}
