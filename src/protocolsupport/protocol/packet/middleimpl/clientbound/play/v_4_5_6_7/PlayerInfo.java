package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddlePlayerInfo;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class PlayerInfo extends MiddlePlayerInfo {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		RecyclableArrayList<ClientBoundPacketData> datas = RecyclableArrayList.create();
		for (Info info : infos) {
			switch (action) {
				case ADD: {
					if (info.previousinfo != null) {
						datas.add(create(info.previousinfo.getName(cache.getLocale()), false, version));
					}
					datas.add(create(info.getName(cache.getLocale()), true, version));
					break;
				}
				case REMOVE: {
					if (info.previousinfo != null) {
						datas.add(create(info.previousinfo.getName(cache.getLocale()), false, version));
					}
					break;
				}
				case DISPLAY_NAME: {
					if (info.previousinfo != null) {
						datas.add(create(info.previousinfo.getName(cache.getLocale()), false, version));
						if (info.displayNameJson != null) {
							datas.add(create(info.getName(cache.getLocale()), true, version));
						} else {
							datas.add(create(info.previousinfo.getUserName(), true, version));
						}
					}
					break;
				}
				default: {
					break;
				}
			}
		}
		return datas;
	}

	private static ClientBoundPacketData create(String name, boolean add, ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_PLAYER_INFO_ID, version);
		StringSerializer.writeString(serializer, version, Utils.clampString(name, 16));
		serializer.writeBoolean(add);
		serializer.writeShort(0);
		return serializer;
	}

}
