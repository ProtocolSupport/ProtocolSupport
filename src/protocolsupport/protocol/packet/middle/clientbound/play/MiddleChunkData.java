package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.BitSet;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.types.ChunkCoord;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.protocol.types.chunk.ChunkSectonBlockData;
import protocolsupport.protocol.types.nbt.NBTCompound;

public abstract class MiddleChunkData extends ClientBoundMiddlePacket {

	protected MiddleChunkData(MiddlePacketInit init) {
		super(init);
	}

	protected ChunkCoord coord;
	protected BitSet blockMask;
	protected NBTCompound heightmaps;
	protected int[] biomes;
	protected ChunkSectonBlockData[] sections;
	protected TileEntity[] tiles;

	@Override
	protected void decode(ByteBuf serverdata) {
		coord = PositionSerializer.readIntChunkCoord(serverdata);

		blockMask = BitSet.valueOf(ArraySerializer.readVarIntLongArray(serverdata));
		heightmaps = ItemStackSerializer.readDirectTag(serverdata);
		biomes = ArraySerializer.readVarIntVarIntArray(serverdata);

		{
			ByteBuf chunkdata = ArraySerializer.readVarIntByteArraySlice(serverdata);
			sections = new ChunkSectonBlockData[blockMask.length()];
			for (int sectionIndex = 0; sectionIndex < sections.length; sectionIndex++) {
				if (blockMask.get(sectionIndex)) {
					sections[sectionIndex] = ChunkSectonBlockData.readFromStream(chunkdata);
				}
			}
		}

		tiles = ArraySerializer.readVarIntTArray(serverdata, TileEntity.class, from -> new TileEntity(ItemStackSerializer.readDirectTag(serverdata)));
	}

}
