package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_11;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleTitle;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Title extends MiddleTitle<RecyclableCollection<ClientBoundPacketData>> {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_TITLE, version);
		serializer.writeVarInt(action.ordinal());
		switch (action) {
			case SET_TITLE: {
				serializer.writeString(titleJson);
				break;
			}
			case SET_SUBTITLE: {
				serializer.writeString(subtitleJson);
				break;
			}
			case SET_ACTION_BAR: {
				serializer.writeString(titleJson);
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