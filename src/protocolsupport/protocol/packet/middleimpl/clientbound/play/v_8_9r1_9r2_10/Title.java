package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10;

import io.netty.handler.codec.EncoderException;
import protocolsupport.api.chat.ChatAPI.MessagePosition;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleTitle;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.Chat;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.serializer.chat.ChatSerializer;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class Title extends MiddleTitle {

	protected final ClientCache clientCache = cache.getClientCache();

	public Title(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		if (action != Action.SET_ACTION_BAR) {
			ClientBoundPacketData title = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_TITLE);
			int actionId = action.ordinal();
			VarNumberSerializer.writeVarInt(title, actionId > 2 ? actionId - 1 : actionId);
			switch (action) {
				case SET_TITLE:
				case SET_SUBTITLE: {
					StringSerializer.writeVarIntUTF8String(title, ChatSerializer.serialize(version, clientCache.getLocale(), message));
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
			codec.writeClientbound(title);
		} else {
			codec.writeClientbound(Chat.create(MessagePosition.HOTBAR, ChatSerializer.serialize(version, clientCache.getLocale(), message)));
		}
	}

}
