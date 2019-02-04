package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.EnumConstantLookups;

public abstract class MiddleBossBar extends ClientBoundMiddlePacket {

	public MiddleBossBar(ConnectionImpl connection) {
		super(connection);
	}

	protected UUID uuid;
	protected Action action;
	protected BaseComponent title;
	protected float percent;
	protected int color;
	protected int divider;
	protected int flags;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		uuid = MiscSerializer.readUUID(serverdata);
		action = MiscSerializer.readVarIntEnum(serverdata, Action.CONSTANT_LOOKUP);
		switch (action) {
			case ADD: {
				title = ChatAPI.fromJSON(StringSerializer.readVarIntUTF8String(serverdata));
				percent = serverdata.readFloat();
				color = VarNumberSerializer.readVarInt(serverdata);
				divider = VarNumberSerializer.readVarInt(serverdata);
				flags = serverdata.readUnsignedByte();
				break;
			}
			case REMOVE: {
				break;
			}
			case UPDATE_PERCENT: {
				percent = serverdata.readFloat();
				break;
			}
			case UPDATE_TITLE: {
				title = ChatAPI.fromJSON(StringSerializer.readVarIntUTF8String(serverdata));
				break;
			}
			case UPDATE_STYLE: {
				color = VarNumberSerializer.readVarInt(serverdata);
				divider = VarNumberSerializer.readVarInt(serverdata);
				break;
			}
			case UPDATE_FLAGS: {
				flags = serverdata.readUnsignedByte();
				break;
			}
		}
	}

	protected enum Action {
		ADD, REMOVE, UPDATE_PERCENT, UPDATE_TITLE, UPDATE_STYLE, UPDATE_FLAGS;
		public static final EnumConstantLookups.EnumConstantLookup<Action> CONSTANT_LOOKUP = new EnumConstantLookups.EnumConstantLookup<>(Action.class);
	}

}
