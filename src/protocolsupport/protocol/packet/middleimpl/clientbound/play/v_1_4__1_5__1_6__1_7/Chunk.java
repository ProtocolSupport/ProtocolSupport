package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5__1_6__1_7;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.legacyremapper.LegacyTileEntityUpdate;
import protocolsupport.protocol.legacyremapper.chunk.ChunkTransformer;
import protocolsupport.protocol.legacyremapper.chunk.EmptyChunk;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChunk;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.protocol.utils.types.NBTTagCompoundWrapper;
import protocolsupport.utils.netty.Compressor;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class Chunk extends MiddleChunk<RecyclableCollection<PacketData>> {

	private final ChunkTransformer transformer = new ChunkTransformer();

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		RecyclableArrayList<PacketData> packets = RecyclableArrayList.create();
		PacketData chunkdata = PacketData.create(ClientBoundPacket.PLAY_CHUNK_SINGLE_ID, version);
		chunkdata.writeInt(chunkX);
		chunkdata.writeInt(chunkZ);
		chunkdata.writeBoolean(full);
		boolean hasSkyLight = storage.hasSkyLightInCurrentDimension();
		if (bitmask == 0 && full) {
			chunkdata.writeShort(1);
			chunkdata.writeShort(0);
			byte[] compressed = EmptyChunk.getPre18ChunkData(hasSkyLight);
			chunkdata.writeInt(compressed.length);
			chunkdata.writeBytes(compressed);
		} else {
			chunkdata.writeShort(bitmask);
			chunkdata.writeShort(0);
			transformer.loadData(data, bitmask, hasSkyLight, full);
			byte[] compressed = Compressor.compressStatic(transformer.toPre18Data(version));
			chunkdata.writeInt(compressed.length);
			chunkdata.writeBytes(compressed);
		}
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
