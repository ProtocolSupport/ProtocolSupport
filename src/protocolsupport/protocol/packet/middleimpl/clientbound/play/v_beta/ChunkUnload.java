package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_beta;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChunkUnload;

public class ChunkUnload extends MiddleChunkUnload {

	public ChunkUnload(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		codec.write(Chunk.createPreChunk(chunk, false));
	}

}
