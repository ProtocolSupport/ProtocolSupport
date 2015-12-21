package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.status.v_1_7;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.status.MiddlePong;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;

public class Pong extends MiddlePong<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeLong(pingId);
		return Collections.singletonList(new PacketData(ClientBoundPacket.STATUS_PONG_ID, serializer));
	}

}
