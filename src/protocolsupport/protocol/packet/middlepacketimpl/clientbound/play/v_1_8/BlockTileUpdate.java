package protocolsupport.protocol.packet.middlepacketimpl.clientbound.play.v_1_8;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middlepacket.clientbound.play.MiddleBlockTileUpdate;
import protocolsupport.protocol.packet.middlepacketimpl.PacketData;
import protocolsupport.protocol.typeremapper.nbt.tileupdate.TileNBTTransformer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class BlockTileUpdate extends MiddleBlockTileUpdate<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_UPDATE_TILE_ID, version);
		serializer.writePosition(position);
		serializer.writeByte(type);
		serializer.writeTag(TileNBTTransformer.transform(type, version, tag));
		return RecyclableSingletonList.create(serializer);
	}

}
