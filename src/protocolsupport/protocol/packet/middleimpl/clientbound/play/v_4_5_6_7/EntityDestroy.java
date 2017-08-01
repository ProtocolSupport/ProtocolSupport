package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityDestroy;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class EntityDestroy extends MiddleEntityDestroy {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		RecyclableCollection<ClientBoundPacketData> datas = RecyclableArrayList.create();
		for (int[] part : Utils.splitArray(entityIds, 120)) {
			ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_ENTITY_DESTROY_ID, version);
			serializer.writeByte(part.length);
			for (int i = 0; i < part.length; i++) {
				serializer.writeInt(part[i]);
			}
			datas.add(serializer);
		}
		return datas;
	}

}
