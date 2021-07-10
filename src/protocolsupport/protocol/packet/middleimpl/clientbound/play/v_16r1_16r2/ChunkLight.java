package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_16r1_16r2;

import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2.AbstractLimitedHeightChunkLight;
import protocolsupport.protocol.typeremapper.chunk.ChunkWriterVaries;
import protocolsupport.utils.CollectionsUtils;

public class ChunkLight extends AbstractLimitedHeightChunkLight {

	public ChunkLight(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData chunklightPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_CHUNK_LIGHT);
		PositionCodec.writeVarIntChunkCoord(chunklightPacket, coord);
		chunklightPacket.writeBoolean(trustEdges);
		VarNumberCodec.writeVarInt(chunklightPacket, CollectionsUtils.getBitSetFirstLong(setSkyLightMask));
		VarNumberCodec.writeVarInt(chunklightPacket, CollectionsUtils.getBitSetFirstLong(setBlockLightMask));
		VarNumberCodec.writeVarInt(chunklightPacket, CollectionsUtils.getBitSetFirstLong(emptySkyLightMask));
		VarNumberCodec.writeVarInt(chunklightPacket, CollectionsUtils.getBitSetFirstLong(emptyBlockLightMask));
		ChunkWriterVaries.writeLight(chunklightPacket, skyLight, setSkyLightMask);
		ChunkWriterVaries.writeLight(chunklightPacket, blockLight, setBlockLightMask);
		codec.writeClientbound(chunklightPacket);
	}

}
