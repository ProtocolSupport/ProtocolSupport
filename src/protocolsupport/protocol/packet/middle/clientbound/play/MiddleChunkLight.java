package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.ChunkCache;
import protocolsupport.protocol.storage.netcache.ChunkCache.CachedChunk;
import protocolsupport.protocol.types.ChunkCoord;
import protocolsupport.protocol.types.chunk.ChunkConstants;
import protocolsupport.utils.Utils;

public abstract class MiddleChunkLight extends ClientBoundMiddlePacket {

	protected final ChunkCache chunkCache = cache.getChunkCache();

	public MiddleChunkLight(ConnectionImpl connection) {
		super(connection);
	}

	protected ChunkCoord coord;
	protected int setSkyLightMask;
	protected int setBlockLightMask;
	protected int emptySkyLightMask;
	protected int emptyBlockLightMask;
	protected final byte[][] skyLight = new byte[ChunkConstants.SECTION_COUNT_LIGHT][];
	protected final  byte[][] blockLight = new byte[ChunkConstants.SECTION_COUNT_LIGHT][];

	protected boolean preChunk;
	protected CachedChunk cachedChunk;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		coord = PositionSerializer.readVarIntChunkCoord(serverdata);
		cachedChunk = chunkCache.get(coord);
		if (cachedChunk != null) {
			preChunk = false;
		} else {
			preChunk = true;
			cachedChunk = chunkCache.add(coord);
		}

		setSkyLightMask = VarNumberSerializer.readVarInt(serverdata);
		setBlockLightMask = VarNumberSerializer.readVarInt(serverdata);
		emptySkyLightMask = VarNumberSerializer.readVarInt(serverdata);
		emptyBlockLightMask = VarNumberSerializer.readVarInt(serverdata);

		if (Utils.isBitSet(setSkyLightMask, 0)) {
			skyLight[0] = ArraySerializer.readVarIntByteArray(serverdata);
		}
		for (int i = 1; i < (ChunkConstants.SECTION_COUNT_LIGHT - 1); i++) {
			if (Utils.isBitSet(setSkyLightMask, i)) {
				byte[] section = ArraySerializer.readVarIntByteArray(serverdata);
				skyLight[i] = section;
				cachedChunk.setSkyLightSection(i - 1, section);
			} else if (Utils.isBitSet(emptySkyLightMask, i)) {
				cachedChunk.setSkyLightSection(i - 1, null);
			}
		}
		if (Utils.isBitSet(setSkyLightMask, ChunkConstants.SECTION_COUNT_LIGHT - 1)) {
			skyLight[ChunkConstants.SECTION_COUNT_LIGHT - 1] = ArraySerializer.readVarIntByteArray(serverdata);
		}

		if (Utils.isBitSet(setBlockLightMask, 0)) {
			blockLight[0] = ArraySerializer.readVarIntByteArray(serverdata);
		}
		for (int sectionNumber = 1; sectionNumber < (ChunkConstants.SECTION_COUNT_LIGHT - 1); sectionNumber++) {
			if (Utils.isBitSet(setBlockLightMask, sectionNumber)) {
				byte[] section = ArraySerializer.readVarIntByteArray(serverdata);
				blockLight[sectionNumber] = section;
				cachedChunk.setBlockLightSection(sectionNumber - 1, section);
			} else if (Utils.isBitSet(emptyBlockLightMask, sectionNumber)) {
				cachedChunk.setBlockLightSection(sectionNumber - 1, null);
			}
		}
		if (Utils.isBitSet(setBlockLightMask, ChunkConstants.SECTION_COUNT_LIGHT - 1)) {
			blockLight[ChunkConstants.SECTION_COUNT_LIGHT - 1] = ArraySerializer.readVarIntByteArray(serverdata);
		}
	}

}
