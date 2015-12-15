package protocolsupport.protocol.transformer.middlepacketimpl.v_1_7.clientbound.play;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleWorldEvent;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;

public class WorldEvent extends MiddleWorldEvent<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeInt(effectId);
		serializer.writeInt(position.getX());
		serializer.writeByte(position.getY());
		serializer.writeInt(position.getZ());
		serializer.writeInt(data);
		serializer.writeBoolean(disableRelative);
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_WORLD_EVENT_ID, serializer));
	}

}
