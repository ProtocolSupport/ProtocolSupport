package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.UUID;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleBossBar extends ClientBoundMiddlePacket {

	protected UUID uuid;
	protected Action action;
	protected String title;
	protected float percent;
	protected int color;
	protected int divider;
	protected int flags;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		uuid = serializer.readUUID();
		action = serializer.readEnum(Action.class);
		switch (action) {
			case ADD: {
				title = serializer.readString();
				percent = serializer.readFloat();
				color = serializer.readVarInt();
				divider = serializer.readVarInt();
				flags = serializer.readUnsignedByte();
				break;
			}
			case REMOVE: {
				break;
			}
			case UPDATE_PERCENT: {
				percent = serializer.readFloat();
				break;
			}
			case UPDATE_TITLE: {
				title = serializer.readString();
				break;
			}
			case UPDATE_STYLE: {
				color = serializer.readVarInt();
				divider = serializer.readVarInt();
				break;
			}
			case UPDATE_FLAGS: {
				flags = serializer.readUnsignedByte();
				break;
			}
		}
	}

	protected enum Action {
		ADD, REMOVE, UPDATE_PERCENT, UPDATE_TITLE, UPDATE_STYLE, UPDATE_FLAGS;
	}

}
