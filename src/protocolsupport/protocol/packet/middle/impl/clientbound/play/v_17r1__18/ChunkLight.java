package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_17r1__18;

import java.util.BitSet;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleChunkLight;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV18;
import protocolsupport.protocol.types.ChunkCoord;

public class ChunkLight extends MiddleChunkLight implements
IClientboundMiddlePacketV17r1,
IClientboundMiddlePacketV17r2,
IClientboundMiddlePacketV18 {

	public ChunkLight(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		io.writeClientbound(create(coord, false, setSkyLightMask, setBlockLightMask, emptySkyLightMask, emptyBlockLightMask, skyLight, blockLight));
	}

	public static ClientBoundPacketData create(
		ChunkCoord coord, boolean trustEdges,
		BitSet setSkyLightMask, BitSet setBlockLightMask,
		BitSet emptySkyLightMask, BitSet emptyBlockLightMask,
		byte[][] skyLight, byte[][] blockLight
	) {
		ClientBoundPacketData chunklightPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_CHUNK_LIGHT);
		PositionCodec.writeVarIntChunkCoord(chunklightPacket, coord);
		writeData(chunklightPacket, trustEdges, setSkyLightMask, setBlockLightMask, emptySkyLightMask, emptyBlockLightMask, skyLight, blockLight);
		return chunklightPacket;
	}

	public static void writeData(
		ClientBoundPacketData data,
		boolean trustEdges,
		BitSet setSkyLightMask, BitSet setBlockLightMask,
		BitSet emptySkyLightMask, BitSet emptyBlockLightMask,
		byte[][] skyLight, byte[][] blockLight
	) {
		data.writeBoolean(trustEdges);
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
