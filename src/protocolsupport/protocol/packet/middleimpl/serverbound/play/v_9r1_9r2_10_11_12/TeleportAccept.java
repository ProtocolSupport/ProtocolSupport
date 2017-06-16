package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10_11_12;

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
