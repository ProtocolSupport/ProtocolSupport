package protocolsupport.protocol.packet.middleimpl.serverbound.play.v1_9_r1__1_9_r2__1_10;

import java.io.IOException;

import org.bukkit.util.Vector;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUseEntity;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class UseEntity extends MiddleUseEntity {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		entityId = serializer.readVarInt();
		action = serializer.readEnum(Action.class);
		switch (action) {
			case INTERACT: {
				usedHand = serializer.readVarInt();
				break;
			}
			case INTERACT_AT: {
				interactedAt = new Vector(serializer.readFloat(), serializer.readFloat(), serializer.readFloat());
				usedHand = serializer.readVarInt();
				break;
			}
			case ATTACK: {
				break;
			}
		}
	}

}
