package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_7;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleSpawnExpOrb;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;

public class SpawnExpOrb extends MiddleSpawnExpOrb<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeVarInt(entityId);
		serializer.writeInt(x);
		serializer.writeInt(y);
		serializer.writeInt(z);
		serializer.writeShort(count);
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_SPAWN_EXP_ORB_ID, serializer));
	}

}
