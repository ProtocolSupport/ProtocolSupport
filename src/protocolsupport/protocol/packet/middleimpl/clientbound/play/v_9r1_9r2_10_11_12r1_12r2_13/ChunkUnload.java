package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2_10_11_12r1_12r2_13;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractChunkCacheChunkUnload;
import protocolsupport.protocol.serializer.PositionSerializer;

public class ChunkUnload extends AbstractChunkCacheChunkUnload {

	public ChunkUnload(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData chunkunload = ClientBoundPacketData.create(ClientBoundPacketType.CLIENTBOUND_PLAY_CHUNK_UNLOAD);
		PositionSerializer.writeIntChunkCoord(chunkunload, chunk);
		codec.writeClientbound(chunkunload);
	}

}
