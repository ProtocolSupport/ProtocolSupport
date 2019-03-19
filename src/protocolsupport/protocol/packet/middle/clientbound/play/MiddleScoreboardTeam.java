package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.HashSet;
import java.util.Set;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.EnumConstantLookups.EnumConstantLookup;

public abstract class MiddleScoreboardTeam extends ClientBoundMiddlePacket {

	public MiddleScoreboardTeam(ConnectionImpl connection) {
		super(connection);
	}

	protected String name;
	protected Mode mode;
	protected BaseComponent displayName;
	protected BaseComponent prefix;
	protected BaseComponent suffix;
	protected int friendlyFire;
	protected String nameTagVisibility;
	protected String collisionRule;
	protected int color;
	protected String[] players;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		name = StringSerializer.readVarIntUTF8String(serverdata);
		mode = MiscSerializer.readByteEnum(serverdata, Mode.CONSTANT_LOOKUP);
		if ((mode == Mode.CREATE) || (mode == Mode.UPDATE)) {
			displayName = ChatAPI.fromJSON(StringSerializer.readVarIntUTF8String(serverdata));
			friendlyFire = serverdata.readUnsignedByte();
			nameTagVisibility = StringSerializer.readVarIntUTF8String(serverdata);
			collisionRule = StringSerializer.readVarIntUTF8String(serverdata);
			color = VarNumberSerializer.readVarInt(serverdata);
			prefix = ChatAPI.fromJSON(StringSerializer.readVarIntUTF8String(serverdata));
			suffix = ChatAPI.fromJSON(StringSerializer.readVarIntUTF8String(serverdata));
		}
		if ((mode == Mode.CREATE) || (mode == Mode.PLAYERS_ADD) || (mode == Mode.PLAYERS_REMOVE)) {
			players = ArraySerializer.readVarIntVarIntUTF8StringArray(serverdata);
		}
	}

	protected final Set<String> teams = new HashSet<>();

	@Override
	public boolean postFromServerRead() {
		switch (mode) {
			case CREATE: {
				return teams.add(name);
			}
			case REMOVE: {
				return teams.remove(name);
			}
			default: {
				return teams.contains(name);
			}
		}
	}

	protected static enum Mode {
		CREATE, REMOVE, UPDATE, PLAYERS_ADD, PLAYERS_REMOVE;
		public static final EnumConstantLookup<Mode> CONSTANT_LOOKUP = new EnumConstantLookup<>(Mode.class);
	}

}
