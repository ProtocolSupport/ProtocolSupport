package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_20;

import java.util.BitSet;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleChunkLight;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV20;
import protocolsupport.protocol.types.ChunkCoord;

public class ChunkLight extends MiddleChunkLight implements
IClientboundMiddlePacketV20 {

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
		writeData(chunklightPacket, setSkyLightMask, setBlockLightMask, emptySkyLightMask, emptyBlockLightMask, skyLight, blockLight);
		return chunklightPacket;
	}

	public static void writeData(
		ClientBoundPacketData data,
		BitSet setSkyLightMask, BitSet setBlockLightMask,
		BitSet emptySkyLightMask, BitSet emptyBlockLightMask,
		byte[][] skyLight, byte[][] blockLight
	) {
		ArrayCodec.writeVarIntLongArray(data, setSkyLightMask.toLongArray());
		ArrayCodec.writeVarIntLongArray(data, setBlockLightMask.toLongArray());
		ArrayCodec.writeVarIntLongArray(data, emptySkyLightMask.toLongArray());
		ArrayCodec.writeVarIntLongArray(data, emptyBlockLightMask.toLongArray());
		encodeLight(data, skyLight);
		encodeLight(data, blockLight);
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
