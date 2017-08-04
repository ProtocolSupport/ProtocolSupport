package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10_11_12r1_12r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleTeleportAccept;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class TeleportAccept extends MiddleTeleportAccept {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		teleportConfirmId = VarNumberSerializer.readVarInt(clientdata);
	}

}
