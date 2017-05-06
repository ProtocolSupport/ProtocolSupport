package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleEntityAction;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class EntityAction extends MiddleEntityAction {

	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		entityId = VarNumberSerializer.readVarInt(clientdata);
		actionId = VarNumberSerializer.readVarInt(clientdata);
		jumpBoost = VarNumberSerializer.readVarInt(clientdata);
		if (version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_8) && (actionId == 6)) {
			actionId = 7;
		}
	}

}
