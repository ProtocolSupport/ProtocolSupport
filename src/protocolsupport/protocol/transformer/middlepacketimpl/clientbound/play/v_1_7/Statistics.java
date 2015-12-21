package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_7;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleStatistics;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Statistics extends MiddleStatistics<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeVarInt(statistics.length);
		for (Statistic stat : statistics) {
			serializer.writeString(stat.name);
			serializer.writeVarInt(stat.value);
		}
		return RecyclableSingletonList.<PacketData>create(PacketData.create(ClientBoundPacket.PLAY_STATISTICS, serializer));
	}

}
