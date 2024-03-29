package protocolsupport.protocol.packet.middle.base.clientbound.play;

import java.util.HashSet;
import java.util.Set;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.MiddlePacketCancelException;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;
import protocolsupport.protocol.utils.EnumConstantLookup;

public abstract class MiddleScoreboardObjective extends ClientBoundMiddlePacket {

	protected MiddleScoreboardObjective(IMiddlePacketInit init) {
		super(init);
	}

	protected final Set<String> objectives = new HashSet<>();

	protected String name;
	protected Mode mode;
	protected BaseComponent value;
	protected Type type;

	@Override
	protected void decode(ByteBuf serverdata) {
		name = StringCodec.readVarIntUTF8String(serverdata);
		mode = MiscDataCodec.readByteEnum(serverdata, Mode.CONSTANT_LOOKUP);
		if (mode != Mode.REMOVE) {
			value = ChatAPI.fromJSON(StringCodec.readVarIntUTF8String(serverdata), true);
			type = MiscDataCodec.readVarIntEnum(serverdata, Type.CONSTANT_LOOKUP);
		}

		switch (mode) {
			case CREATE: {
				if (!objectives.add(name)) {
					throw MiddlePacketCancelException.INSTANCE;
				}
				break;
			}
			case REMOVE: {
				if (!objectives.remove(name)) {
					throw MiddlePacketCancelException.INSTANCE;
				}
				break;
			}
			default: {
				if (!objectives.contains(name)) {
					throw MiddlePacketCancelException.INSTANCE;
				}
				break;
			}
		}
	}


	protected enum Mode {
		CREATE, REMOVE, UPDATE;
		public static final EnumConstantLookup<Mode> CONSTANT_LOOKUP = new EnumConstantLookup<>(Mode.class);
	}

	protected enum Type {
		INTEGER, HEARTS;
		public static final EnumConstantLookup<Type> CONSTANT_LOOKUP = new EnumConstantLookup<>(Type.class);
	}

}
