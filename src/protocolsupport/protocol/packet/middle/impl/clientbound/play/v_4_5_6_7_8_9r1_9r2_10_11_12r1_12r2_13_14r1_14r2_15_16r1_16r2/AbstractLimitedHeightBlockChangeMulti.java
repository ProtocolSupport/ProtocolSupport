package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

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
