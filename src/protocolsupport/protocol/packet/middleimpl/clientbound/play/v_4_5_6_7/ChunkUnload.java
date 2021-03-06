package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractChunkCacheChunkUnload;

public class ChunkUnload extends AbstractChunkCacheChunkUnload {

	public ChunkUnload(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData chunkunload = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_CHUNK_SINGLE);
		PositionCodec.writeIntChunkCoord(chunkunload, chunk);
		chunkunload.writeBoolean(true);
		chunkunload.writeShort(0);
		chunkunload.writeShort(0);
		chunkunload.writeInt(0);
		codec.writeClientbound(chunkunload);
	}

}
