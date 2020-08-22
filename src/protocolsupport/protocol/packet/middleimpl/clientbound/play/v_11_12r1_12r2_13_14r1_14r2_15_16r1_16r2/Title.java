package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleTitle;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.serializer.chat.ChatSerializer;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class Title extends MiddleTitle {

	public Title(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void writeToClient() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_TITLE);
		VarNumberSerializer.writeVarInt(serializer, action.ordinal());
		switch (action) {
			case SET_TITLE:
			case SET_SUBTITLE:
			case SET_ACTION_BAR: {
				StringSerializer.writeVarIntUTF8String(serializer, ChatSerializer.serialize(version, clientCache.getLocale(), message));
				break;
			}
			case SET_TIMES: {
				serializer.writeInt(fadeIn);
				serializer.writeInt(stay);
				serializer.writeInt(fadeOut);
				break;
			}
			case HIDE:
			case RESET: {
				break;
			}
		}
		codec.write(serializer);
	}

}