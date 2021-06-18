package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2_15;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17.AbstractLimitedHeightChunkLight;
import protocolsupport.protocol.types.chunk.ChunkConstants;
import protocolsupport.utils.BitUtils;

public class ChunkLight extends AbstractLimitedHeightChunkLight {

	public ChunkLight(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData chunklightPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_CHUNK_LIGHT);
		PositionCodec.writeVarIntChunkCoord(chunklightPacket, coord);
		VarNumberCodec.writeVarInt(chunklightPacket, limitedSetSkyLightMask);
		VarNumberCodec.writeVarInt(chunklightPacket, limitedSetBlockLightMask);
		VarNumberCodec.writeVarInt(chunklightPacket, limitedEmptySkyLightMask);
		VarNumberCodec.writeVarInt(chunklightPacket, limitedEmptyBlockLightMask);
		encodeLight(chunklightPacket, skyLight, limitedSetSkyLightMask, limitedHeightOffset);
		encodeLight(chunklightPacket, blockLight, limitedSetBlockLightMask, limitedHeightOffset);
		codec.writeClientbound(chunklightPacket);
	}

	protected static void encodeLight(ClientBoundPacketData packet, byte[][] lightArrays, int mask, int offset) {
		for (int sectionIndex = 0; sectionIndex < ChunkConstants.LEGACY_LIMITED_HEIGHT_CHUNK_LIGHT_SECTIONS; sectionIndex++) {
			if (BitUtils.isIBitSet(mask, sectionIndex)) {
				ArrayCodec.writeVarIntByteArray(packet, lightArrays[sectionIndex + offset]);
			}
		}
	}

}
