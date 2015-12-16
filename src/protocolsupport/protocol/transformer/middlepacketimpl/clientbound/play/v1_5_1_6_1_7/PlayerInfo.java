package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v1_5_1_6_1_7;

import java.util.ArrayList;
import java.util.Collection;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddlePlayerInfo;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.protocol.transformer.middlepacketimpl.SupportedVersions;

@SupportedVersions({ProtocolVersion.MINECRAFT_1_7_10, ProtocolVersion.MINECRAFT_1_7_5, ProtocolVersion.MINECRAFT_1_6_4, ProtocolVersion.MINECRAFT_1_6_2, ProtocolVersion.MINECRAFT_1_5_2})
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
