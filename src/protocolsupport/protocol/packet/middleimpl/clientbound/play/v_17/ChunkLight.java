package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_17;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChunkLight;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;

public class ChunkLight extends MiddleChunkLight {

	public ChunkLight(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData chunklightPacket = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_CHUNK_LIGHT);
		PositionSerializer.writeVarIntChunkCoord(chunklightPacket, coord);
		chunklightPacket.writeBoolean(trustEdges);
		ArraySerializer.writeVarIntLongArray(chunklightPacket, setSkyLightMask.toLongArray());
		ArraySerializer.writeVarIntLongArray(chunklightPacket, setBlockLightMask.toLongArray());
		ArraySerializer.writeVarIntLongArray(chunklightPacket, emptySkyLightMask.toLongArray());
		ArraySerializer.writeVarIntLongArray(chunklightPacket, emptyBlockLightMask.toLongArray());
		encodeLight(chunklightPacket, skyLight);
		encodeLight(chunklightPacket, blockLight);
		codec.writeClientbound(chunklightPacket);
	}

	protected static void encodeLight(ClientBoundPacketData packet, byte[][] lightArrays) {
		MiscSerializer.writeVarIntCountPrefixedType(packet, lightArrays, (lightArraysTo, lLightArrays) -> {
			int count = 0;
			for (int i = 0; i < lLightArrays.length; i++) {
				byte[] light = lightArrays[i];
				if (light != null) {
					count++;
					ArraySerializer.writeVarIntByteArray(lightArraysTo, light);
				}
			}
			return count;
		});
	}

}
