package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChunk;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2.BlockTileUpdate;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.typeremapper.chunk.ChunkTransformer;
import protocolsupport.protocol.typeremapper.chunk.ChunkTransformer.BlockFormat;
import protocolsupport.protocol.typeremapper.chunk.EmptyChunk;
import protocolsupport.protocol.typeremapper.tileentity.TileNBTRemapper;
import protocolsupport.protocol.utils.types.TileEntityType;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class Chunk extends MiddleChunk {

	private final ChunkTransformer transformer = ChunkTransformer.create(BlockFormat.SHORT);

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
			ArraySerializer.writeByteArray(chunkdata, version, EmptyChunk.get18ChunkData(hasSkyLight));
		} else {
			chunkdata.writeShort(bitmask);
			transformer.loadData(data, bitmask, hasSkyLight, full);
			ArraySerializer.writeByteArray(chunkdata, version, transformer.toLegacyData(version));
		}
		packets.add(chunkdata);
		for (NBTTagCompoundWrapper tile : tiles) {
			packets.add(BlockTileUpdate.createPacketData(
				version,
				TileEntityType.getByRegistryId(TileNBTRemapper.getTileType(tile)),
				TileNBTRemapper.getPosition(tile),
				tile
			));
		}
		return packets;
	}

}
