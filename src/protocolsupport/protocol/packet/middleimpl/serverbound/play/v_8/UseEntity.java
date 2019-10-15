package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8;

import org.bukkit.util.Vector;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUseEntity;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.UsedHand;

public class UseEntity extends MiddleUseEntity {

	public UseEntity(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		entityId = VarNumberSerializer.readVarInt(clientdata);
		action = MiscSerializer.readVarIntEnum(clientdata, Action.CONSTANT_LOOKUP);
		if (action == Action.INTERACT_AT) {
			interactedAt = new Vector(clientdata.readFloat(), clientdata.readFloat(), clientdata.readFloat());
		}
		hand = UsedHand.MAIN;
	}

}
