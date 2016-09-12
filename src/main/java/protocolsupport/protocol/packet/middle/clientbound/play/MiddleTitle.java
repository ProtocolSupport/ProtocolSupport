package protocolsupport.protocol.packet.middle.clientbound.play;

import java.io.IOException;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleTitle<T> extends ClientBoundMiddlePacket<T> {

	protected Action action;
	protected String titleJson;
	protected String subtitleJson;
	protected int fadeIn;
	protected int stay;
	protected int fadeOut;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		action = Action.values()[serializer.readVarInt()];
		switch (action) {
			case SET_TITLE: {
				titleJson = serializer.readString();
				break;
			}
			case SET_SUBTITLE: {
				subtitleJson = serializer.readString();
				break;
			}
			case SET_TIMES: {
				fadeIn = serializer.readInt();
				stay = serializer.readInt();
				fadeOut = serializer.readInt();
				break;
			}
			case HIDE:
			case RESET: {
				break;
			}
		}
	}

	protected static enum Action {
		SET_TITLE, SET_SUBTITLE, SET_TIMES, HIDE, RESET
	}

}
