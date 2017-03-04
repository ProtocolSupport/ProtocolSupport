package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleTitle extends ClientBoundMiddlePacket {

	protected Action action;
	protected String titleJson;
	protected String subtitleJson;
	protected int fadeIn;
	protected int stay;
	protected int fadeOut;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		action = Action.values()[VarNumberSerializer.readVarInt(serverdata)];
		switch (action) {
			case SET_TITLE: {
				titleJson = StringSerializer.readString(serverdata, ProtocolVersion.getLatest());
				break;
			}
			case SET_SUBTITLE: {
				subtitleJson = StringSerializer.readString(serverdata, ProtocolVersion.getLatest());
				break;
			}
			case SET_ACTION_BAR: {
				titleJson = StringSerializer.readString(serverdata, ProtocolVersion.getLatest());
				break;
			}
			case SET_TIMES: {
				fadeIn = serverdata.readInt();
				stay = serverdata.readInt();
				fadeOut = serverdata.readInt();
				break;
			}
			case HIDE:
			case RESET: {
				break;
			}
		}
	}

	protected static enum Action {
		SET_TITLE, SET_SUBTITLE, SET_ACTION_BAR, SET_TIMES, HIDE, RESET
	}

}
