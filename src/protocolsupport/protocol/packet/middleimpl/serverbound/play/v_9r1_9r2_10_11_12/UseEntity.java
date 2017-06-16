package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10_11_12;

import org.bukkit.util.Vector;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUseEntity;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class UseEntity extends MiddleUseEntity {

	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		entityId = VarNumberSerializer.readVarInt(clientdata);
		action = MiscSerializer.readEnum(clientdata, Action.class);
		switch (action) {
			case INTERACT: {
				usedHand = VarNumberSerializer.readVarInt(clientdata);
				break;
			}
			case INTERACT_AT: {
				interactedAt = new Vector(clientdata.readFloat(), clientdata.readFloat(), clientdata.readFloat());
				usedHand = VarNumberSerializer.readVarInt(clientdata);
				break;
			}
			case ATTACK: {
				break;
			}
		}
	}

}
