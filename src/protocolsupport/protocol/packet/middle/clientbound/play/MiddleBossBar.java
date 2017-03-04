package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleBossBar extends ClientBoundMiddlePacket {

	protected UUID uuid;
	protected Action action;
	protected String title;
	protected float percent;
	protected int color;
	protected int divider;
	protected int flags;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		uuid = MiscSerializer.readUUID(serverdata);
		action = MiscSerializer.readEnum(serverdata, Action.class);
		switch (action) {
			case ADD: {
				title = StringSerializer.readString(serverdata, ProtocolVersion.getLatest());
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
				title = StringSerializer.readString(serverdata, ProtocolVersion.getLatest());
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
	}

}
