package protocolsupport.protocol.transformer.middlepacketimpl.v_1_7.clientbound.play;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleBlockTileUpdate;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;

public class BlockTileUpdate extends MiddleBlockTileUpdate<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeInt(position.getX());
		serializer.writeShort(position.getY());
		serializer.writeInt(position.getZ());
		serializer.writeByte(type);
		serializer.writeTag(tag);
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_UPDATE_TILE_ID, serializer));
	}

}
