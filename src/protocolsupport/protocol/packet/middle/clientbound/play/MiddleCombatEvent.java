package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleCombatEvent extends ClientBoundMiddlePacket {

	protected Type type;
	protected int duration;
	protected int playerId;
	protected int entityId;
	protected String message;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		type = MiscSerializer.readEnum(serverdata, Type.class);
		switch (type) {
			case ENTER_COMBAT: {
				break;
			}
			case END_COMBAT: {
				duration = VarNumberSerializer.readVarInt(serverdata);
				entityId = serverdata.readInt();
				break;
			}
			case ENTITY_DEAD: {
				playerId = VarNumberSerializer.readVarInt(serverdata);
				entityId = serverdata.readInt();
				message = StringSerializer.readString(serverdata, ProtocolVersion.getLatest());
				break;
			}
		}
	}

	protected static enum Type {
		ENTER_COMBAT, END_COMBAT, ENTITY_DEAD
	}

}
