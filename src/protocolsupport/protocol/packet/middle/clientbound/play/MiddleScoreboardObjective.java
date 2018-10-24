package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.EnumConstantLookups.EnumConstantLookup;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public abstract class MiddleScoreboardObjective extends ClientBoundMiddlePacket {

	public MiddleScoreboardObjective(ConnectionImpl connection) {
		super(connection);
	}

	protected String name;
	protected Mode mode;
	protected BaseComponent value;
	protected Type type;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		name = StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC, 16);
		mode = MiscSerializer.readByteEnum(serverdata, Mode.CONSTANT_LOOKUP);
		if (mode != Mode.REMOVE) {
			value = ChatAPI.fromJSON(StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC));
			type = MiscSerializer.readVarIntEnum(serverdata, Type.CONSTANT_LOOKUP);
		}
	}

	protected static enum Mode {
		CREATE, REMOVE, UPDATE;
		public static final EnumConstantLookup<Mode> CONSTANT_LOOKUP = new EnumConstantLookup<>(Mode.class);
	}

	protected static enum Type {
		INTEGER, HEARTS;
		public static final EnumConstantLookup<Type> CONSTANT_LOOKUP = new EnumConstantLookup<>(Type.class);
	}

}
