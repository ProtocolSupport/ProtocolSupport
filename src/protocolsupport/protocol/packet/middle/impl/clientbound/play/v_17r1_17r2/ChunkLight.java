package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_17r1_17r2;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleChunkLight;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r2;

public class ChunkLight extends MiddleChunkLight implements
IClientboundMiddlePacketV17r1,
IClientboundMiddlePacketV17r2 {

	public ChunkLight(IMiddlePacketInit init) {
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
		io.writeClientbound(chunklightPacket);
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
