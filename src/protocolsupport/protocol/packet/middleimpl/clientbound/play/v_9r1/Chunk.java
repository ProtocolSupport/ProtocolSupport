package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChunk;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.BlockTileUpdate;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.basic.TileNBTRemapper;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.chunk.ChunkTransformerBB;
import protocolsupport.protocol.typeremapper.chunk.ChunkTransformerVariesLegacy;
import protocolsupport.protocol.utils.types.TileEntityType;
import protocolsupport.utils.netty.Allocator;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class Chunk extends MiddleChunk {

	public Chunk(ConnectionImpl connection) {
		super(connection);
	}

	protected final ChunkTransformerBB transformer = new ChunkTransformerVariesLegacy(LegacyBlockData.REGISTRY.getTable(connection.getVersion()));

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		transformer.loadData(data, bitmask, cache.getAttributesCache().hasSkyLightInCurrentDimension(), full);
		ProtocolVersion version = connection.getVersion();
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		ClientBoundPacketData chunkdata = ClientBoundPacketData.create(ClientBoundPacket.PLAY_CHUNK_SINGLE_ID);
		chunkdata.writeInt(chunkX);
		chunkdata.writeInt(chunkZ);
		chunkdata.writeBoolean(full);
		VarNumberSerializer.writeVarInt(chunkdata, bitmask);
		Allocator.withTempBuffer(tempbuffer -> {
			transformer.toLegacyData(tempbuffer);
			ArraySerializer.writeByteArray(chunkdata, version, tempbuffer);
		});
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
