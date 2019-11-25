package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7_8;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnGlobal;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class SpawnGlobal extends MiddleSpawnGlobal {

	public SpawnGlobal(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData spawnglobal = codec.allocClientBoundPacketData(PacketType.CLIENTBOUND_PLAY_SPAWN_GLOBAL);
		VarNumberSerializer.writeVarInt(spawnglobal, entity.getId());
		spawnglobal.writeByte(entity.getType().getNetworkTypeId());
		spawnglobal.writeInt((int) (x * 32));
		spawnglobal.writeInt((int) (y * 32));
		spawnglobal.writeInt((int) (z * 32));
		codec.write(spawnglobal);
	}

}
