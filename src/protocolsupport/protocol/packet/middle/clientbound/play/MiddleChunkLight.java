package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.BitSet;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.ChunkCoord;

public abstract class MiddleChunkLight extends ClientBoundMiddlePacket {

	protected MiddleChunkLight(MiddlePacketInit init) {
		super(init);
	}

	protected ChunkCoord coord;
	protected boolean trustEdges;
	protected BitSet setSkyLightMask;
	protected BitSet setBlockLightMask;
	protected BitSet emptySkyLightMask;
	protected BitSet emptyBlockLightMask;
	protected byte[][] skyLight;
	protected byte[][] blockLight;

	@Override
	protected void decode(ByteBuf serverdata) {
		coord = PositionSerializer.readVarIntChunkCoord(serverdata);
		trustEdges = serverdata.readBoolean();

		setSkyLightMask = BitSet.valueOf(ArraySerializer.readVarIntLongArray(serverdata));
		setBlockLightMask = BitSet.valueOf(ArraySerializer.readVarIntLongArray(serverdata));
		emptySkyLightMask = BitSet.valueOf(ArraySerializer.readVarIntLongArray(serverdata));
		emptyBlockLightMask = BitSet.valueOf(ArraySerializer.readVarIntLongArray(serverdata));

		skyLight = new byte[setSkyLightMask.length()][];
		VarNumberSerializer.readVarInt(serverdata); //skylight data count
		for (int sectionIndex = 0; sectionIndex < skyLight.length; sectionIndex++) {
			skyLight[sectionIndex] = setSkyLightMask.get(sectionIndex) ? ArraySerializer.readVarIntByteArray(serverdata) : null;
		}
		VarNumberSerializer.readVarInt(serverdata); //blocklight data count
		blockLight = new byte[setBlockLightMask.length()][];
		for (int sectionIndex = 0; sectionIndex < blockLight.length; sectionIndex++) {
			blockLight[sectionIndex] = setBlockLightMask.get(sectionIndex) ? ArraySerializer.readVarIntByteArray(serverdata) : null;
		}
	}

}
