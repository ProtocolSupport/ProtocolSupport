package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_9_r2__1_10__1_11;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.legacyremapper.LegacyTileEntityUpdate;
import protocolsupport.protocol.legacyremapper.chunk.ChunkTransformer;
import protocolsupport.protocol.legacyremapper.chunk.ChunkTransformer.BlockFormat;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChunk;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.nms.NBTTagCompoundWrapper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

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
			if (version.isBefore(ProtocolVersion.MINECRAFT_1_11)) {
				LegacyTileEntityUpdate.setLegacyType(tile);
			}
			serializer.writeTag(tile);
		}
		return RecyclableSingletonList.create(serializer);
	}

}
