package protocolsupport.protocol.typeremapper.chunk;

import java.util.ListIterator;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.utils.netty.Allocator;

public class ChunkTransformerPE extends ChunkTransformer {

	protected static final int flag_runtime = 1;
	protected static final int bitsPerBlock__PE = 16; //TODO: vary size for optimisation!
	protected final IntArrayList states = new IntArrayList();
	
	@Override
	public byte[] toLegacyData(ProtocolVersion version) {
		ArrayBasedIdRemappingTable table = PEDataValues.BLOCK_ID;
		ByteBuf chunkdata = Allocator.allocateBuffer();
		try {
			chunkdata.writeByte(sections.length);
			for (int i = 0; i < sections.length; i++) {
				chunkdata.writeByte(1); //New subchunk version!
				//VarNumberSerializer.writeVarInt(chunkdata, 1 /*section == null ? 0 : 1*/); //TODO: new beta, blockstorage count (first is blocks second is water, we only do first for now)
				ChunkSection section = sections[i];
				if (section != null) {
					chunkdata.writeByte((bitsPerBlock__PE << 1) | flag_runtime);
					for (int x = 0; x < 16 ; x++) {
						for (int z = 0; z < 16; z++) {
							for (int y = 0; y < 16; y++) {
								int blockstate = table.getRemap(getBlockState(section, x, y, z));
								if (!states.contains(blockstate)) {
									states.add(blockstate);
								}
								chunkdata.writeShortLE(states.indexOf(blockstate));
							}
						}
					}
					VarNumberSerializer.writeSVarInt(chunkdata, states.size());
					for (ListIterator<Integer> iter = states.listIterator(); iter.hasNext(); ) {
						VarNumberSerializer.writeSVarInt(chunkdata, iter.next());
						iter.remove();
					}
				} else {
					//TODO: write better empty section in new beta!
					chunkdata.writeByte((1 << 1) | flag_runtime);
					chunkdata.writeZero(512);
					VarNumberSerializer.writeSVarInt(chunkdata, 1);
					VarNumberSerializer.writeSVarInt(chunkdata, 0);
				}
			}
			chunkdata.writeZero(512); //heightmap.
			chunkdata.writeBytes(biomeData); //BiomeData
			return MiscSerializer.readAllBytes(chunkdata);
		} finally {
			chunkdata.release();
		}
	}

	private static int getBlockState(ChunkSection section, int x, int y, int z) {
		return section.blockdata.getBlockState((y << 8) | (z << 4) | (x));
	}

}
