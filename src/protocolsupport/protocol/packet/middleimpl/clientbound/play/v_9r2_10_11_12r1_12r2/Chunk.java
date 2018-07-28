package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r2_10_11_12r1_12r2;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChunk;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.basic.TileNBTRemapper;
import protocolsupport.protocol.typeremapper.chunk.ChunkTransformer;
import protocolsupport.protocol.typeremapper.chunk.ChunkTransformerVariesLegacy;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class Chunk extends MiddleChunk {

	protected final ChunkTransformer transformer = new ChunkTransformerVariesLegacy();

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_CHUNK_SINGLE_ID);
		serializer.writeInt(chunkX);
		serializer.writeInt(chunkZ);
		serializer.writeBoolean(full);
		VarNumberSerializer.writeVarInt(serializer, bitmask);
		transformer.loadData(data, bitmask, cache.getAttributesCache().hasSkyLightInCurrentDimension(), full);
		ArraySerializer.writeByteArray(serializer, version, transformer.toLegacyData(version));
		VarNumberSerializer.writeVarInt(serializer, tiles.length);
		for (NBTTagCompoundWrapper tile : tiles) {
			ItemStackSerializer.writeTag(serializer, version, TileNBTRemapper.remap(version, tile));
		}
		return RecyclableSingletonList.create(serializer);
	}

}
