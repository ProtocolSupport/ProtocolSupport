package protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.clientbound.play;

import java.util.ArrayList;
import java.util.Collection;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddlePlayerInfo;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;

public class PlayerInfo extends MiddlePlayerInfo<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) {
		Collection<PacketData> datas = new ArrayList<PacketData>();
		for (Info info : infos) {			
			switch (action) {
				case ADD: {
					PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
					serializer.writeString(info.username);
					serializer.writeBoolean(true);
					serializer.writeShort(0);
					datas.add(new PacketData(ClientBoundPacket.PLAY_PLAYER_INFO_ID, serializer));
					break;
				}
				case REMOVE: {
					PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
					serializer.writeString(info.username);
					serializer.writeBoolean(false);
					serializer.writeShort(0);
					datas.add(new PacketData(ClientBoundPacket.PLAY_PLAYER_INFO_ID, serializer));
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
