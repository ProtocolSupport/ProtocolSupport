package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChunkLight;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.chunk.ChunkConstants;
import protocolsupport.utils.BitUtils;

public class ChunkLight extends MiddleChunkLight {

	public ChunkLight(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData chunklight = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_CHUNK_LIGHT);
		PositionSerializer.writeVarIntChunkCoord(chunklight, coord);
		VarNumberSerializer.writeVarInt(chunklight, setSkyLightMask);
		VarNumberSerializer.writeVarInt(chunklight, setBlockLightMask);
		VarNumberSerializer.writeVarInt(chunklight, emptySkyLightMask);
		VarNumberSerializer.writeVarInt(chunklight, emptyBlockLightMask);
		for (int i = 0; i < ChunkConstants.SECTION_COUNT_LIGHT; i++) {
			if (BitUtils.isIBitSet(setSkyLightMask, i)) {
				ArraySerializer.writeVarIntByteArray(chunklight, skyLight[i]);
			}
		}
		for (int i = 0; i < ChunkConstants.SECTION_COUNT_LIGHT; i++) {
			if (BitUtils.isIBitSet(setBlockLightMask, i)) {
				ArraySerializer.writeVarIntByteArray(chunklight, blockLight[i]);
			}
		}
		codec.write(chunklight);
	}

}
