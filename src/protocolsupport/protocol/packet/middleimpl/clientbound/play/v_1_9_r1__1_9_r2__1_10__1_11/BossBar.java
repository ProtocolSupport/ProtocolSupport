package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_9_r1__1_9_r2__1_10__1_11;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBossBar;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class BossBar extends MiddleBossBar<RecyclableCollection<ClientBoundPacketData>> {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_BOSS_BAR, version);
		serializer.writeUUID(uuid);
		serializer.writeEnum(action);
		switch (action) {
			case ADD: {
				serializer.writeString(title);
				serializer.writeFloat(percent);
				serializer.writeVarInt(color);
				serializer.writeVarInt(divider);
				serializer.writeByte(flags);
				break;
			}
			case REMOVE: {
				break;
			}
			case UPDATE_PERCENT: {
				serializer.writeFloat(percent);
				break;
			}
			case UPDATE_TITLE: {
				serializer.writeString(title);
				break;
			}
			case UPDATE_STYLE: {
				serializer.writeVarInt(color);
				serializer.writeVarInt(divider);
				break;
			}
			case UPDATE_FLAGS: {
				serializer.writeByte(flags);
				break;
			}
		}
		return RecyclableSingletonList.create(serializer);
	}

}
