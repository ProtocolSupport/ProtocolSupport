package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5__1_6__1_7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityDestroy;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class EntityDestroy extends MiddleEntityDestroy<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) {
		RecyclableCollection<PacketData> datas = RecyclableArrayList.create();
		for (int[] part : Utils.splitArray(entityIds, 120)) {
			PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_ENTITY_DESTROY_ID, version);
			serializer.writeByte(part.length);
			for (int i = 0; i < part.length; i++) {
				serializer.writeInt(part[i]);
			}
			datas.add(serializer);
		}
		return datas;
	}

}
