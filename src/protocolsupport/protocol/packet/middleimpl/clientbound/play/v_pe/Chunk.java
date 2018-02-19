package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChunk;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.chunk.ChunkTransformer;
import protocolsupport.protocol.typeremapper.chunk.ChunkTransformer.BlockFormat;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.typeremapper.tileentity.TileNBTRemapper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class Chunk extends MiddleChunk {

	private final ChunkTransformer transformer = ChunkTransformer.create(BlockFormat.PE);

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		if (full) { //Send only full chunks packets to PE.
			cache.markSentChunk(chunkX, chunkZ);
			ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.CHUNK_DATA, version);
			VarNumberSerializer.writeSVarInt(serializer, chunkX);
			VarNumberSerializer.writeSVarInt(serializer, chunkZ);
			transformer.loadData(data, bitmask, cache.hasSkyLightInCurrentDimension(), full);
			ByteBuf chunkdata = Unpooled.buffer();
			chunkdata.writeBytes(transformer.toLegacyData(version));
			chunkdata.writeByte(0); //borders
			VarNumberSerializer.writeSVarInt(chunkdata, 0); //extra data
			for (NBTTagCompoundWrapper tile : tiles) {
				ItemStackSerializer.writeTag(chunkdata, true, version, TileNBTRemapper.remap(version, tile));
			}
			ArraySerializer.writeByteArray(serializer, version, chunkdata);
			return RecyclableSingletonList.create(serializer);
		} else { //Request a full chunk back from the server.
			try {
				ByteArrayOutputStream requestChunk = new ByteArrayOutputStream();
				DataOutputStream serializer = new DataOutputStream(requestChunk);
				serializer.writeInt(chunkX);
				serializer.writeInt(chunkZ);
				serializer.flush();
				connection.receivePacket(ServerPlatform.get().getPacketFactory().createInboundCustomPayloadPacket(
					PERequestListener.CHUNK_REQ, requestChunk.toByteArray()
				));
			} catch (IOException e) {
			}
			return RecyclableEmptyList.get();
		}
	}

	public static ClientBoundPacketData createEmptyChunk(ProtocolVersion version, int chunkX, int chunkZ) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.CHUNK_DATA, version);
		VarNumberSerializer.writeSVarInt(serializer, chunkX);
		VarNumberSerializer.writeSVarInt(serializer, chunkZ);
		ByteBuf chunkdata = Unpooled.buffer();
		chunkdata.writeByte(1); //section count
		chunkdata.writeZero(4096); //blocks
		chunkdata.writeZero(2048); //block data
		chunkdata.writeZero(512); //heightmap
		chunkdata.writeZero(256); //biomes
		chunkdata.writeByte(0); //borders
		VarNumberSerializer.writeSVarInt(chunkdata, 0); //extra data
		ArraySerializer.writeByteArray(serializer, version, chunkdata);
		return serializer;
	}

}
