package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.UUIDCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.utils.EnumConstantLookup;

public abstract class MiddleBossBar extends ClientBoundMiddlePacket {

	protected MiddleBossBar(MiddlePacketInit init) {
		super(init);
	}

	protected UUID uuid;
	protected Action action;
	protected BaseComponent title;
	protected float percent;
	protected int color;
	protected int divider;
	protected int flags;

	@Override
	protected void decode(ByteBuf serverdata) {
		uuid = UUIDCodec.readUUID2L(serverdata);
		action = MiscDataCodec.readVarIntEnum(serverdata, Action.CONSTANT_LOOKUP);
		switch (action) {
			case ADD: {
				title = ChatAPI.fromJSON(StringCodec.readVarIntUTF8String(serverdata), true);
				percent = serverdata.readFloat();
				color = VarNumberCodec.readVarInt(serverdata);
				divider = VarNumberCodec.readVarInt(serverdata);
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
				title = ChatAPI.fromJSON(StringCodec.readVarIntUTF8String(serverdata), true);
				break;
			}
			case UPDATE_STYLE: {
				color = VarNumberCodec.readVarInt(serverdata);
				divider = VarNumberCodec.readVarInt(serverdata);
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
		public static final EnumConstantLookup<Action> CONSTANT_LOOKUP = new EnumConstantLookup<>(Action.class);
	}

}
