package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8;

import org.bukkit.util.Vector;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUseEntity;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class UseEntity extends MiddleUseEntity {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		entityId = VarNumberSerializer.readVarInt(clientdata);
		action = MiscSerializer.readEnum(clientdata, Action.class);
		if (action == Action.INTERACT_AT) {
			interactedAt = new Vector(clientdata.readFloat(), clientdata.readFloat(), clientdata.readFloat());
		}
	}

}
