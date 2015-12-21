package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleTimeUpdate;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;

public class TimeUpdate extends MiddleTimeUpdate<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeLong(worldAge);
		serializer.writeLong(timeOfDay);
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_UPDATE_TIME_ID, serializer));
	}

}
