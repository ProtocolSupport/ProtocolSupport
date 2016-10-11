package protocolsupport.protocol.packet.middle.clientbound.play;

import java.io.IOException;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleCombatEvent<T> extends ClientBoundMiddlePacket<T> {

	protected Type type;
	protected int duration;
	protected int playerId;
	protected int entityId;
	protected String message;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		type = serializer.readEnum(Type.class);
		switch (type) {
			case ENTER_COMBAT: {
				break;
			}
			case END_COMBAT: {
				duration = serializer.readVarInt();
				entityId = serializer.readInt();
				break;
			}
			case ENTITY_DEAD: {
				playerId = serializer.readVarInt();
				entityId = serializer.readInt();
				message = serializer.readString();
				break;
			}
		}
	}

	protected static enum Type {
		ENTER_COMBAT, END_COMBAT, ENTITY_DEAD
	}

}
