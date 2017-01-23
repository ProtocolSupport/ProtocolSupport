package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_9_r2__1_10__1_11;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.legacyremapper.chunk.ChunkTransformer;
import protocolsupport.protocol.legacyremapper.chunk.ChunkTransformer.BlockFormat;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChunk;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.nbt.tileupdate.TileNBTTransformer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class Chunk extends MiddleChunk<RecyclableCollection<ClientBoundPacketData>> {

	private final ChunkTransformer transformer = ChunkTransformer.create(BlockFormat.VARIES);

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_CHUNK_SINGLE_ID, version);
		serializer.writeInt(chunkX);
		serializer.writeInt(chunkZ);
		serializer.writeBoolean(full);
		serializer.writeVarInt(bitmask);
		transformer.loadData(data, bitmask, cache.hasSkyLightInCurrentDimension(), full);
		serializer.writeByteArray(transformer.toLegacyData(version));
		serializer.writeVarInt(tiles.length);
		for (NBTTagCompoundWrapper tile : tiles) {
			serializer.writeTag(TileNBTTransformer.transform(version, tile));
		}
		return RecyclableSingletonList.create(serializer);
	}

}
