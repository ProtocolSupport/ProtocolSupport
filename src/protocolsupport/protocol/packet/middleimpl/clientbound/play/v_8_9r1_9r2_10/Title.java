package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10;

import io.netty.handler.codec.EncoderException;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleTitle;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.legacy.chat.LegacyChatJson;

public class Title extends MiddleTitle {

	public Title(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData title = codec.allocClientBoundPacketData(PacketType.CLIENTBOUND_PLAY_TITLE);
		int actionId = action.ordinal();
		VarNumberSerializer.writeVarInt(title, actionId > 2 ? actionId - 1 : actionId);
		switch (action) {
			case SET_TITLE:
			case SET_SUBTITLE: {
				StringSerializer.writeVarIntUTF8String(title, ChatAPI.toJSON(LegacyChatJson.convert(version, cache.getAttributesCache().getLocale(), message)));
				break;
			}
			case SET_TIMES: {
				title.writeInt(fadeIn);
				title.writeInt(stay);
				title.writeInt(fadeOut);
				break;
			}
			case HIDE:
			case RESET: {
				break;
			}
			default: {
				throw new EncoderException("Should not reach here");
			}
		}
		codec.write(title);
	}

}
