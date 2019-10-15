package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_beta;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleGameStateChange;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class GameStateChange extends MiddleGameStateChange {

	public GameStateChange(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
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
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_GAME_STATE_CHANGE);
		serializer.writeByte(type);
		return RecyclableSingletonList.create(serializer);
	}

}
