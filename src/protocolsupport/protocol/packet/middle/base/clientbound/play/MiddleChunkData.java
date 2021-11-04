package protocolsupport.protocol.packet.middle.base.clientbound.play;

import java.util.BitSet;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;
import protocolsupport.protocol.types.ChunkCoord;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.protocol.types.chunk.ChunkSectonBlockData;
import protocolsupport.protocol.types.nbt.NBTCompound;

public abstract class MiddleChunkData extends ClientBoundMiddlePacket {

	protected MiddleChunkData(IMiddlePacketInit init) {
		super(init);
	}

	protected ChunkCoord coord;
	protected BitSet mask;
	protected NBTCompound heightmaps;
	protected int[] biomes;
	protected ChunkSectonBlockData[] sections;
	protected TileEntity[] tiles;

	@Override
	protected void decode(ByteBuf serverdata) {
		coord = PositionCodec.readIntChunkCoord(serverdata);

		mask = BitSet.valueOf(ArrayCodec.readVarIntLongArray(serverdata));
		heightmaps = ItemStackCodec.readDirectTag(serverdata);
		biomes = ArrayCodec.readVarIntVarIntArray(serverdata);

		{
			ByteBuf chunkdata = ArrayCodec.readVarIntByteArraySlice(serverdata);
			sections = new ChunkSectonBlockData[mask.length()];
			for (int sectionIndex = 0; sectionIndex < sections.length; sectionIndex++) {
				if (mask.get(sectionIndex)) {
					sections[sectionIndex] = ChunkSectonBlockData.readFromStream(chunkdata);
				}
			}
		}

		tiles = ArrayCodec.readVarIntTArray(serverdata, TileEntity.class, from -> new TileEntity(ItemStackCodec.readDirectTag(serverdata)));
	}

}
