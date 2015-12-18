package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_7;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleBlockAction;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;

public class BlockAction extends MiddleBlockAction<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeInt(position.getX());
		serializer.writeShort(position.getY());
		serializer.writeInt(position.getZ());
		serializer.writeByte(info1);
		serializer.writeByte(info2);
		serializer.writeVarInt(type);
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_BLOCK_ACTION_ID, serializer));
	}

}
