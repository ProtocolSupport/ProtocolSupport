package protocolsupport.protocol.packet.middleimpl.serverbound.play.v1_9_r1__1_9_r2__1_10__1_11;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleTeleportAccept;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class TeleportAccept extends MiddleTeleportAccept {

	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		teleportConfirmId = VarNumberSerializer.readVarInt(clientdata);
	}

}
