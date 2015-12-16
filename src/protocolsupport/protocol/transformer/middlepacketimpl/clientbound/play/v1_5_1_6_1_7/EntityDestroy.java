package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v1_5_1_6_1_7;

import java.util.ArrayList;
import java.util.Collection;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleEntityDestroy;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.utils.Utils;

public class EntityDestroy extends MiddleEntityDestroy<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) {
		Collection<PacketData> datas = new ArrayList<PacketData>();
		for (int[] part : Utils.splitArray(entityIds, 120)) {
			PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
			serializer.writeByte(part.length);
			for (int i = 0; i < part.length; i++) {
				serializer.writeInt(part[i]);
			}
			datas.add(new PacketData(ClientBoundPacket.PLAY_ENTITY_DESTROY_ID, serializer));
		}
		return datas;
	}

}
