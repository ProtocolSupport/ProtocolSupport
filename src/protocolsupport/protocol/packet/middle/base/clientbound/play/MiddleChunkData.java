package protocolsupport.protocol.packet.middle.base.clientbound.play;

import java.util.BitSet;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.types.ChunkCoord;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.protocol.types.TileEntityType;
import protocolsupport.protocol.types.chunk.ChunkSectionData;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.utils.MiscUtils;

public abstract class MiddleChunkData extends ClientBoundMiddlePacket {

	protected MiddleChunkData(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	protected ChunkCoord coord;
	protected NBTCompound heightmaps;
	protected ChunkSectionData[] sections;
	protected TileEntity[] tiles;
	protected BitSet setSkyLightMask;
	protected BitSet setBlockLightMask;
	protected BitSet emptySkyLightMask;
	protected BitSet emptyBlockLightMask;
	protected byte[][] skyLight;
	protected byte[][] blockLight;

	@Override
	protected void decode(ByteBuf serverdata) {
		coord = PositionCodec.readIntChunkCoord(serverdata);

		heightmaps = ItemStackCodec.readDirectTag(serverdata);

		{
			byte bitsPerBiomeGlobal = (byte) MiscUtils.ceilLog2(clientCache.getBiomesCount());
			ByteBuf chunkdata = ArrayCodec.readVarIntByteArraySlice(serverdata);
			sections = new ChunkSectionData[clientCache.getHeight() >> 4];
			for (int sectionIndex = 0; sectionIndex < sections.length; sectionIndex++) {
				sections[sectionIndex] = ChunkSectionData.decode(chunkdata, bitsPerBiomeGlobal);
			}
		}

		tiles = ArrayCodec.readVarIntTArray(serverdata, TileEntity.class, from -> {
			int xz = from.readUnsignedByte();
			int y = from.readShort();
			TileEntityType type = TileEntityType.getByNetworkId(VarNumberCodec.readVarInt(from));
			NBTCompound tag = ItemStackCodec.readDirectTag(serverdata);
			return new TileEntity(type, new Position((coord.getX() << 4) | (xz >> 4), y, (coord.getZ() << 4) | (xz & 0xF)), tag);
		});

		setSkyLightMask = BitSet.valueOf(ArrayCodec.readVarIntLongArray(serverdata));
		setBlockLightMask = BitSet.valueOf(ArrayCodec.readVarIntLongArray(serverdata));
		emptySkyLightMask = BitSet.valueOf(ArrayCodec.readVarIntLongArray(serverdata));
		emptyBlockLightMask = BitSet.valueOf(ArrayCodec.readVarIntLongArray(serverdata));

		skyLight = new byte[setSkyLightMask.length()][];
		VarNumberCodec.readVarInt(serverdata); //skylight data count
		for (int sectionIndex = 0; sectionIndex < skyLight.length; sectionIndex++) {
			skyLight[sectionIndex] = setSkyLightMask.get(sectionIndex) ? ArrayCodec.readVarIntByteArray(serverdata) : null;
		}
		VarNumberCodec.readVarInt(serverdata); //blocklight data count
		blockLight = new byte[setBlockLightMask.length()][];
		for (int sectionIndex = 0; sectionIndex < blockLight.length; sectionIndex++) {
			blockLight[sectionIndex] = setBlockLightMask.get(sectionIndex) ? ArrayCodec.readVarIntByteArray(serverdata) : null;
		}
	}

}
