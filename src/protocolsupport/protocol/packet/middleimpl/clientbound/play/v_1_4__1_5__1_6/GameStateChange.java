package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5__1_6;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleGameStateChange;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class GameStateChange extends MiddleGameStateChange<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		switch (type) {
			case 1: {
				type = 2;
				break;
			}
			case 2: {
				type = 1;
				break;
			}
			default: {
				break;
			}
		}
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_GAME_STATE_CHANGE_ID, version);
		serializer.writeByte(type);
		serializer.writeByte((int) value);
		return RecyclableSingletonList.create(serializer);
	}

}
