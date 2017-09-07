package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChunk;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.chunk.ChunkTransformer;
import protocolsupport.protocol.typeremapper.chunk.ChunkTransformer.BlockFormat;
import protocolsupport.protocol.typeremapper.chunk.EmptyChunk;
import protocolsupport.protocol.typeremapper.tileentity.TileNBTRemapper;
import protocolsupport.protocol.utils.types.TileEntityType;
import protocolsupport.utils.netty.Compressor;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class Chunk extends MiddleChunk {

	private final ChunkTransformer transformer = ChunkTransformer.create(BlockFormat.BYTE);

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		ClientBoundPacketData chunkdata = ClientBoundPacketData.create(ClientBoundPacket.PLAY_CHUNK_SINGLE_ID, version);
		chunkdata.writeInt(chunkX);
		chunkdata.writeInt(chunkZ);
		chunkdata.writeBoolean(full);
		boolean hasSkyLight = cache.hasSkyLightInCurrentDimension();
		if ((bitmask == 0) && full) {
			chunkdata.writeShort(1);
			chunkdata.writeShort(0);
			byte[] compressed = EmptyChunk.getPre18ChunkData(hasSkyLight);
			chunkdata.writeInt(compressed.length);
			chunkdata.writeBytes(compressed);
		} else {
			chunkdata.writeShort(bitmask);
			chunkdata.writeShort(0);
			transformer.loadData(data, bitmask, hasSkyLight, full);
			byte[] compressed = Compressor.compressStatic(transformer.toLegacyData(version));
			chunkdata.writeInt(compressed.length);
			chunkdata.writeBytes(compressed);
		}
		packets.add(chunkdata);
		for (NBTTagCompoundWrapper tile : tiles) {
			packets.add(BlockTileUpdate.createPacketData(
				version,
				cache.getLocale(),
				TileEntityType.getByRegistryId(TileNBTRemapper.getTileType(tile)),
				TileNBTRemapper.getPosition(tile),
				tile
			));
		}
		return packets;
	}

}
