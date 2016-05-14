package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_9;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.legacyremapper.LegacyTileEntityUpdate;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChunk;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8_1_9.BlockTileUpdate;
import protocolsupport.protocol.utils.types.NBTTagCompoundWrapper;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class Chunk extends MiddleChunk<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		RecyclableArrayList<PacketData> packets = RecyclableArrayList.create();
		PacketData chunkdata = PacketData.create(ClientBoundPacket.PLAY_CHUNK_SINGLE_ID, version);
		chunkdata.writeInt(chunkX);
		chunkdata.writeInt(chunkZ);
		chunkdata.writeBoolean(full);
		chunkdata.writeVarInt(bitmask);
		chunkdata.writeVarInt(data.length);
		chunkdata.writeBytes(data);
		packets.add(chunkdata);
		for (NBTTagCompoundWrapper tile : tiles) {
			packets.add(BlockTileUpdate.createPacketData(
				version,
				LegacyTileEntityUpdate.getPosition(tile),
				LegacyTileEntityUpdate.getUpdateType(tile).ordinal(),
				tile
			));
		}
		return packets;
	}

}
