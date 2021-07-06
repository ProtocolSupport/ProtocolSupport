package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_17r1_17r2;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChunkLight;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class ChunkLight extends MiddleChunkLight {

	public ChunkLight(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData chunklightPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_CHUNK_LIGHT);
		PositionCodec.writeVarIntChunkCoord(chunklightPacket, coord);
		chunklightPacket.writeBoolean(trustEdges);
		ArrayCodec.writeVarIntLongArray(chunklightPacket, setSkyLightMask.toLongArray());
		ArrayCodec.writeVarIntLongArray(chunklightPacket, setBlockLightMask.toLongArray());
		ArrayCodec.writeVarIntLongArray(chunklightPacket, emptySkyLightMask.toLongArray());
		ArrayCodec.writeVarIntLongArray(chunklightPacket, emptyBlockLightMask.toLongArray());
		encodeLight(chunklightPacket, skyLight);
		encodeLight(chunklightPacket, blockLight);
		codec.writeClientbound(chunklightPacket);
	}

	protected static void encodeLight(ClientBoundPacketData packet, byte[][] lightArrays) {
		MiscDataCodec.writeVarIntCountPrefixedType(packet, lightArrays, (lightArraysTo, lLightArrays) -> {
			int count = 0;
			for (int i = 0; i < lLightArrays.length; i++) {
				byte[] light = lightArrays[i];
				if (light != null) {
					count++;
					ArrayCodec.writeVarIntByteArray(lightArraysTo, light);
				}
			}
			return count;
		});
	}

}
