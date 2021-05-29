package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.ChunkCoord;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.protocol.types.chunk.ChunkConstants;
import protocolsupport.protocol.types.chunk.ChunkSectonBlockData;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.utils.BitUtils;

public abstract class MiddleChunkData extends ClientBoundMiddlePacket {

	protected MiddleChunkData(MiddlePacketInit init) {
		super(init);
	}

	protected ChunkCoord coord;
	protected boolean full;
	protected int blockMask;
	protected NBTCompound heightmaps;
	protected int[] biomes;
	protected final ChunkSectonBlockData[] sections = new ChunkSectonBlockData[ChunkConstants.SECTION_COUNT_BLOCKS];
	protected TileEntity[] tiles;

	@Override
	protected void decode(ByteBuf serverdata) {
		coord = PositionSerializer.readIntChunkCoord(serverdata);
		full = serverdata.readBoolean();

		blockMask = VarNumberSerializer.readVarInt(serverdata);
		heightmaps = ItemStackSerializer.readDirectTag(serverdata);
		if (full) {
			biomes = ArraySerializer.readVarIntVarIntArray(serverdata);
		}

		{
			ByteBuf chunkdata = ArraySerializer.readVarIntByteArraySlice(serverdata);
			for (int sectionNumber = 0; sectionNumber < ChunkConstants.SECTION_COUNT_BLOCKS; sectionNumber++) {
				if (BitUtils.isIBitSet(blockMask, sectionNumber)) {
					sections[sectionNumber] = ChunkSectonBlockData.readFromStream(chunkdata);
				}
			}
		}

		tiles = ArraySerializer.readVarIntTArray(serverdata, TileEntity.class, from -> new TileEntity(ItemStackSerializer.readDirectTag(serverdata)));
	}

}
