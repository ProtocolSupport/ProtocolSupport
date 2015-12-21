package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_7;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleGameStateChange;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;

public class GameStateChange extends MiddleGameStateChange<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeByte(type);
		serializer.writeFloat(value);
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_GAME_STATE_CHANGE_ID, serializer));
	}

}
