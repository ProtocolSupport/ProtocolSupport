package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__16r2;

import protocolsupport.protocol.packet.middle.MiddlePacketCancelException;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleBlockChangeMulti;
import protocolsupport.protocol.types.chunk.ChunkConstants;

public abstract class AbstractLimitedHeightBlockChangeMulti extends MiddleBlockChangeMulti {

	protected AbstractLimitedHeightBlockChangeMulti(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void handle() {
		if ((chunkSection < 0) || (chunkSection >= ChunkConstants.LEGACY_LIMITED_HEIGHT_CHUNK_BLOCK_SECTIONS)) {
			throw MiddlePacketCancelException.INSTANCE;
		}
	}

}
