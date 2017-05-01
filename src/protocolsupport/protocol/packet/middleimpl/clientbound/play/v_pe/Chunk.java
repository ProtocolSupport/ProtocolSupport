package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.legacyremapper.chunk.ChunkTransformer;
import protocolsupport.protocol.legacyremapper.chunk.ChunkTransformer.BlockFormat;
import protocolsupport.protocol.legacyremapper.pe.PEPacketIDs;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChunk;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ByteArraySerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.tileentity.TileNBTRemapper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class Chunk extends MiddleChunk {

	private final ChunkTransformer transformer = ChunkTransformer.create(BlockFormat.PE);

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		cache.markSentChunk(chunkX, chunkZ);
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.CHUNK_DATA, version);
		VarNumberSerializer.writeSVarInt(serializer, chunkX);
		VarNumberSerializer.writeSVarInt(serializer, chunkZ);
		transformer.loadData(data, bitmask, cache.hasSkyLightInCurrentDimension(), full);
		ByteBuf chunkdata = Unpooled.buffer();
		chunkdata.writeBytes(transformer.toLegacyData(version));
		chunkdata.writeByte(0); //borders???
		VarNumberSerializer.writeSVarInt(chunkdata, 0); //extra data???
		for (NBTTagCompoundWrapper tile : tiles) {
			ItemStackSerializer.writeTag(chunkdata, version, TileNBTRemapper.remap(version, tile));
		}
		ByteArraySerializer.writeByteArray(serializer, version, chunkdata);
		return RecyclableSingletonList.create(serializer);
	}

	public static ClientBoundPacketData createEmptyChunk(ProtocolVersion version, int chunkX, int chunkZ) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.CHUNK_DATA, version);
		VarNumberSerializer.writeSVarInt(serializer, chunkX);
		VarNumberSerializer.writeSVarInt(serializer, chunkZ);
		ByteBuf chunkdata = Unpooled.buffer();
		chunkdata.writeByte(0); //section count
		chunkdata.writeByte(0); //borders???
		VarNumberSerializer.writeSVarInt(chunkdata, 0); //extra data???
		ByteArraySerializer.writeByteArray(serializer, version, chunkdata);
		return serializer;
	}

}
