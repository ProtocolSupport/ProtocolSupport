package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_6_1_7;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddlePlayerAbilities;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;

public class PlayerAbilities extends MiddlePlayerAbilities<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeByte(flags);
		serializer.writeFloat(flyspeed);
		serializer.writeFloat(walkspeed);
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_ABILITIES_ID, serializer));
	}

}
