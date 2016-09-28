package protocolsupport.protocol.packet.middleimpl.serverbound.play.v1_9_r1__1_9_r2;

import java.io.IOException;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleTeleportAccept;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class TeleportAccept extends MiddleTeleportAccept {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		teleportAcceptId = serializer.readVarInt();
	}

}
