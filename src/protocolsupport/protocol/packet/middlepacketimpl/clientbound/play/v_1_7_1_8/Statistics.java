package protocolsupport.protocol.packet.middlepacketimpl.clientbound.play.v_1_7_1_8;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middlepacket.clientbound.play.MiddleStatistics;
import protocolsupport.protocol.packet.middlepacketimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Statistics extends MiddleStatistics<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_STATISTICS, version);
		serializer.writeVarInt(statistics.length);
		for (Statistic stat : statistics) {
			serializer.writeString(stat.name);
			serializer.writeVarInt(stat.value);
		}
		return RecyclableSingletonList.create(serializer);
	}

}
