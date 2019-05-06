package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_11_12r1_12r2_13_14;

import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleTitle;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.legacy.chat.LegacyChatJson;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Title extends MiddleTitle {

	public Title(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_TITLE_ID);
		VarNumberSerializer.writeVarInt(serializer, action.ordinal());
		switch (action) {
			case SET_TITLE:
			case SET_SUBTITLE:
			case SET_ACTION_BAR: {
				StringSerializer.writeString(serializer, version, ChatAPI.toJSON(LegacyChatJson.convert(version, cache.getAttributesCache().getLocale(), message)));
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
		return RecyclableSingletonList.create(serializer);
	}

}