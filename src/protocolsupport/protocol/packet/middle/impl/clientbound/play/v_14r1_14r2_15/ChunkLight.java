package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_14r1_14r2_15;

import java.util.BitSet;

import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV14r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV14r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV15;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2.AbstractLimitedHeightChunkLight;
import protocolsupport.protocol.typeremapper.chunk.ChunkBlockdataLegacyWriterPaletted;
import protocolsupport.protocol.types.ChunkCoord;
import protocolsupport.utils.CollectionsUtils;

public class ChunkLight extends AbstractLimitedHeightChunkLight implements
IClientboundMiddlePacketV14r1,
IClientboundMiddlePacketV14r2,
IClientboundMiddlePacketV15 {

	public ChunkLight(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		io.writeClientbound(create(coord, setSkyLightMask, setBlockLightMask, emptySkyLightMask, emptyBlockLightMask, skyLight, blockLight));
	}

	public static ClientBoundPacketData create(
		ChunkCoord coord,
		BitSet setSkyLightMask, BitSet setBlockLightMask,
		BitSet emptySkyLightMask, BitSet emptyBlockLightMask,
		byte[][] skyLight, byte[][] blockLight
	) {
		ClientBoundPacketData chunklightPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_CHUNK_LIGHT);
		PositionCodec.writeVarIntChunkCoord(chunklightPacket, coord);
		VarNumberCodec.writeVarInt(chunklightPacket, CollectionsUtils.getBitSetFirstLong(setSkyLightMask));
		VarNumberCodec.writeVarInt(chunklightPacket, CollectionsUtils.getBitSetFirstLong(setBlockLightMask));
		VarNumberCodec.writeVarInt(chunklightPacket, CollectionsUtils.getBitSetFirstLong(emptySkyLightMask));
		VarNumberCodec.writeVarInt(chunklightPacket, CollectionsUtils.getBitSetFirstLong(emptyBlockLightMask));
		ChunkBlockdataLegacyWriterPaletted.writeLight(chunklightPacket, skyLight, setSkyLightMask);
		ChunkBlockdataLegacyWriterPaletted.writeLight(chunklightPacket, blockLight, setBlockLightMask);
		return chunklightPacket;
	}

}
