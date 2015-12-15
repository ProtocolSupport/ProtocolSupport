package protocolsupport.protocol.transformer.middlepacketimpl.v_1_5_1_6.clientbound.play;

import java.util.Collection;
import java.util.Collections;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleSpawnExpOrb;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;

public class SpawnExpOrb extends MiddleSpawnExpOrb<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) {
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeInt(entityId);
		serializer.writeInt(x);
		serializer.writeInt(y);
		serializer.writeInt(z);
		serializer.writeShort(count);
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_SPAWN_EXP_ORB_ID, serializer));
	}

}
