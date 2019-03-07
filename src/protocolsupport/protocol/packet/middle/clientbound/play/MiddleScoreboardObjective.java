package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.HashSet;
import java.util.Set;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.EnumConstantLookups.EnumConstantLookup;

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
		name = StringSerializer.readVarIntUTF8String(serverdata);
		mode = MiscSerializer.readByteEnum(serverdata, Mode.CONSTANT_LOOKUP);
		if (mode != Mode.REMOVE) {
			value = ChatAPI.fromJSON(StringSerializer.readVarIntUTF8String(serverdata));
			type = MiscSerializer.readVarIntEnum(serverdata, Type.CONSTANT_LOOKUP);
		}
	}

	protected final Set<String> objectives = new HashSet<>();

	@Override
	public boolean postFromServerRead() {
		switch (mode) {
			case CREATE: {
				return objectives.add(name);
			}
			case REMOVE: {
				return objectives.remove(name);
			}
			default: {
				return objectives.contains(name);
			}
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
