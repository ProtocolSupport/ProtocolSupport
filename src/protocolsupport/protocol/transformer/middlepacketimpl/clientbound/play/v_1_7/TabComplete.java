package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_7;

import java.util.Collection;
import java.util.Collections;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleTabComplete;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;

public class TabComplete extends MiddleTabComplete<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) {
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeVarInt(matches.length);
		for (String match : matches) {
			serializer.writeString(match);
		}
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_TAB_COMPLETE_ID, serializer));
	}

}
