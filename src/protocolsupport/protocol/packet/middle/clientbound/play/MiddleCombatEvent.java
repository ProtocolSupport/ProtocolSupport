package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.EnumConstantLookups;

public abstract class MiddleCombatEvent extends ClientBoundMiddlePacket {

	public MiddleCombatEvent(ConnectionImpl connection) {
		super(connection);
	}

	protected Type type;
	protected int duration;
	protected int playerId;
	protected int entityId;
	protected String message;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		type = MiscSerializer.readVarIntEnum(serverdata, Type.CONSTANT_LOOKUP);
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
				message = StringSerializer.readVarIntUTF8String(serverdata);
				break;
			}
		}
	}

	protected static enum Type {
		ENTER_COMBAT, END_COMBAT, ENTITY_DEAD;
		public static final EnumConstantLookups.EnumConstantLookup<Type> CONSTANT_LOOKUP = new EnumConstantLookups.EnumConstantLookup<>(Type.class);
	}

}
