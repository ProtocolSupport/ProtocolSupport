package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8;

import java.io.IOException;

import org.bukkit.util.Vector;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUseEntity;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class UseEntity extends MiddleUseEntity {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		entityId = serializer.readVarInt();
		action = serializer.readVarInt();
		if (action == 2) {
			interactedAt = new Vector(serializer.readFloat(), serializer.readFloat(), serializer.readFloat());
		}
	}

}
