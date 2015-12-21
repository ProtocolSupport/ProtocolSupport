package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddlePlayerInfo;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class PlayerInfo extends MiddlePlayerInfo<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) {
		RecyclableArrayList<PacketData> datas = RecyclableArrayList.create();
		for (Info info : infos) {			
			switch (action) {
				case ADD: {
					PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
					serializer.writeString(info.username);
					serializer.writeBoolean(true);
					serializer.writeShort(0);
					datas.add(PacketData.create(ClientBoundPacket.PLAY_PLAYER_INFO_ID, serializer));
					break;
				}
				case REMOVE: {
					PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
					serializer.writeString(info.username);
					serializer.writeBoolean(false);
					serializer.writeShort(0);
					datas.add(PacketData.create(ClientBoundPacket.PLAY_PLAYER_INFO_ID, serializer));
					break;
				}
				default: {
					break;
				}
			}
		}
		return datas;
	}

}
