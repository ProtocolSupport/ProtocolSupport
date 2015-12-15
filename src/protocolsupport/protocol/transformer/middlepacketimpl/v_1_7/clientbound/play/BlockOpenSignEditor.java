package protocolsupport.protocol.transformer.middlepacketimpl.v_1_7.clientbound.play;

import java.util.Collection;
import java.util.Collections;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleBlockOpenSignEditor;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;

public class BlockOpenSignEditor extends MiddleBlockOpenSignEditor<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) {
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeInt(position.getX());
		serializer.writeInt(position.getY());
		serializer.writeInt(position.getZ());
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_SIGN_EDITOR_ID, serializer));
	}

}
