package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5__1_6__1_7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddlePlayerInfo;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class PlayerInfo extends MiddlePlayerInfo<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) {
		RecyclableArrayList<PacketData> datas = RecyclableArrayList.create();
		for (Info info : infos) {
			switch (action) {
				case ADD: {
					if (info.previousinfo != null) {
						datas.add(createData(info.previousinfo.getName(), false, version));
					}
					datas.add(createData(info.getName(), true, version));
					break;
				}
				case REMOVE: {
					if (info.previousinfo != null) {
						datas.add(createData(info.previousinfo.getName(), false, version));
					}
					break;
				}
				case DISPLAY_NAME: {
					if (info.previousinfo != null) {
						datas.add(createData(info.previousinfo.getName(), false, version));
						if (info.displayNameJson != null) {
							datas.add(createData(info.getName(), true, version));
						} else {
							datas.add(createData(info.previousinfo.getUserName(), true, version));
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

	static PacketData createData(String name, boolean add, ProtocolVersion version) {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_PLAYER_INFO_ID, version);
		serializer.writeString(Utils.clampString(name, 16));
		serializer.writeBoolean(add);
		serializer.writeShort(0);
		return serializer;
	}

}
