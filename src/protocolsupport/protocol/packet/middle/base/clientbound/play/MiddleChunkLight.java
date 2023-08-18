package protocolsupport.protocol.packet.middle.base.clientbound.play;

import java.util.BitSet;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;
import protocolsupport.protocol.types.ChunkCoord;

public abstract class MiddleChunkLight extends ClientBoundMiddlePacket {

	protected MiddleChunkLight(IMiddlePacketInit init) {
		super(init);
	}

	protected ChunkCoord coord;
	protected BitSet setSkyLightMask;
	protected BitSet setBlockLightMask;
	protected BitSet emptySkyLightMask;
	protected BitSet emptyBlockLightMask;
	protected byte[][] skyLight;
	protected byte[][] blockLight;

	@Override
	protected void decode(ByteBuf serverdata) {
		coord = PositionCodec.readVarIntChunkCoord(serverdata);

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
