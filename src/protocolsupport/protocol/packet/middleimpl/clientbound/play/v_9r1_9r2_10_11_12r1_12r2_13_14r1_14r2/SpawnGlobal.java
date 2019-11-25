package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2;

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
		spawnglobal.writeDouble(x);
		spawnglobal.writeDouble(y);
		spawnglobal.writeDouble(z);
		codec.write(spawnglobal);
	}

}
