package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11;

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
