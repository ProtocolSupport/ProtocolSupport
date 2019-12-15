package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
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

public abstract class MiddleChunk extends ClientBoundMiddlePacket {

	public MiddleChunk(ConnectionImpl connection) {
		super(connection);
	}

	protected ChunkCoord coord;
	protected boolean full;
	protected int blockMask;
	protected NBTCompound heightmaps;
	protected final int[] biomeData = new int[1024];
	protected final ChunkSectonBlockData[] sections = new ChunkSectonBlockData[ChunkConstants.SECTION_COUNT_BLOCKS];
	protected TileEntity[] tiles;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		coord = PositionSerializer.readIntChunkCoord(serverdata);
		full = serverdata.readBoolean();
		blockMask = VarNumberSerializer.readVarInt(serverdata);
		heightmaps = ItemStackSerializer.readDirectTag(serverdata);
		if (full) {
			for (int i = 0; i < biomeData.length; i++) {
				biomeData[i] = serverdata.readInt();
			}
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

	protected int getBiomeId(int x, int y, int z) {
		return biomeData[(((y >> 2) & 63) << 4) | (((z >> 2) & 3) << 2) | ((x >> 2) & 3)];
	}

}
