package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.listeners.InternalPluginMessageRequest;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChunk;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.basic.TileNBTRemapper;
import protocolsupport.protocol.typeremapper.chunk.ChunkTransformer;
import protocolsupport.protocol.typeremapper.chunk.ChunkTransformerPE;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class Chunk extends MiddleChunk {

	public Chunk(ConnectionImpl connection) {
		super(connection);
	}

	private final ChunkTransformer transformer = new ChunkTransformerPE();

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		if (full || (bitmask == 0xFFFF)) { //Only send full or 'full' chunks to PE.
			ProtocolVersion version = connection.getVersion();
			cache.getPEChunkMapCache().markSent(chunkX, chunkZ);
			ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.CHUNK_DATA);
			VarNumberSerializer.writeSVarInt(serializer, chunkX);
			VarNumberSerializer.writeSVarInt(serializer, chunkZ);
			transformer.loadData(data, bitmask, cache.getAttributesCache().hasSkyLightInCurrentDimension(), full);
			ByteBuf chunkdata = Unpooled.buffer();
			chunkdata.writeBytes(transformer.toLegacyData(version));
			chunkdata.writeByte(0); //borders
			VarNumberSerializer.writeSVarInt(chunkdata, 0); //extra data
			for (NBTTagCompoundWrapper tile : tiles) {
				ItemStackSerializer.writeTag(chunkdata, true, version, TileNBTRemapper.remap(version, tile));
			}
			ArraySerializer.writeByteArray(serializer, version, chunkdata);
			return RecyclableSingletonList.create(serializer);
		} else { //Request a full chunk.
			InternalPluginMessageRequest.receivePluginMessageRequest(connection, new InternalPluginMessageRequest.ChunkUpdateRequest(chunkX, chunkZ));
			return RecyclableEmptyList.get();
		}
	}

	public static ClientBoundPacketData createEmptyChunk(ProtocolVersion version, int chunkX, int chunkZ) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.CHUNK_DATA);
		VarNumberSerializer.writeSVarInt(serializer, chunkX);
		VarNumberSerializer.writeSVarInt(serializer, chunkZ);
		ByteBuf chunkdata = Unpooled.buffer();
		chunkdata.writeByte(1); //1st section
	    chunkdata.writeByte(1); //New subchunk version!
	    //VarNumberSerializer.writeVarInt(chunkdata, 0); //blockstorage count (first is blocks second is water, we only do first for now) TODO: new beta, write zero and be done?
	    chunkdata.writeByte((1 << 1) | 1);  //Runtimeflag and palette id.
	    chunkdata.writeZero(512);
	    VarNumberSerializer.writeSVarInt(chunkdata, 1); //Palette size
	    VarNumberSerializer.writeSVarInt(chunkdata, 0); //Air
		chunkdata.writeZero(512); //heightmap.
		chunkdata.writeZero(256); //Biomedata.
		chunkdata.writeByte(0); //borders
		VarNumberSerializer.writeSVarInt(chunkdata, 0); //extra data
		ArraySerializer.writeByteArray(serializer, version, chunkdata);
		return serializer;
	}

}
