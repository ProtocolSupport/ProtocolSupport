package protocolsupport.protocol.packet.middlepacketimpl.serverbound.play.v_1_8;

import java.io.IOException;

import net.minecraft.server.v1_9_R1.Vector3f;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middlepacket.serverbound.play.MiddleUseEntity;

public class UseEntity extends MiddleUseEntity {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) throws IOException {
		entityId = serializer.readVarInt();
		action = serializer.readVarInt();
		if (action == 2) {
			interactedAt = new Vector3f(serializer.readFloat(), serializer.readFloat(), serializer.readFloat());
		}
	}

}
